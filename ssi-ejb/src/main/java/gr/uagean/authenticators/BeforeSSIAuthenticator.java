/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.authenticators;

import gr.uaegean.pojo.KeycloakSessionTO;
import gr.uaegean.services.PropertiesService;
import gr.uaegean.singleton.MemcacheUtils;
import java.io.IOException;
import java.util.UUID;
import javax.ws.rs.core.Response;
import net.spy.memcached.MemcachedClient;
import org.jboss.logging.Logger;
import org.keycloak.OAuth2Constants;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nikos
 */
public class BeforeSSIAuthenticator extends AbstractSSIAuthenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static final Logger LOG = Logger.getLogger(BeforeEidasAuthenticator.class);
    private MemcachedClient mcc;
    private PropertiesService propServ;

    @Override
    public void action(AuthenticationFlowContext afc) {
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession ks, RealmModel rm, UserModel um) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession ks, RealmModel rm, UserModel um) {
    }

    @Override
    public void close() {

    }

    @Override
    public void authenticateImpl(AuthenticationFlowContext context) {
        try {
            KeycloakSession session = context.getSession();
            RealmModel realm = context.getRealm();

            this.mcc = MemcacheUtils.getCache();
            this.propServ = new PropertiesService();

            // create a new sessionId
            String ssiSessionId = UUID.randomUUID().toString();

            // grab oidc params
            String response_type = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.RESPONSE_TYPE);
            String client_id = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.CLIENT_ID);
            String redirect_uri = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.REDIRECT_URI);
            String state = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.STATE);
            String scope = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.SCOPE);
            int expiresInSec = 300;
            //Transfer Object that will be cached
            KeycloakSessionTO ksTO = new KeycloakSessionTO(state, response_type, client_id, redirect_uri, state, scope);
            LOG.info("will add with key:" + state + " object " + ksTO.toString());
            mcc.add(ssiSessionId, expiresInSec, ksTO);

            //
            context.getAuthenticationSession().getClientScopes().stream().forEach(scp -> {
                LOG.info("this client: " + client_id + " has the following scopes requests:" + scp);
            });

            // GET the QR Code
            RestTemplate restTemplate = new RestTemplate();
            String uportHelperMsHost = StringUtils.isEmpty(this.propServ.getProp("UPORTHELPER")) ? "http://localhost:3000" : this.propServ.getProp("UPORTHELPER");
            String callback = StringUtils.isEmpty(this.propServ.getProp("CALLBACK")) ? "http://localhost:3000" : this.propServ.getProp("CALLBACK");

            String resourceUrl = uportHelperMsHost + "/connectionRequest?eidas=true&ssiSessionId=" + ssiSessionId + "&callback=" + callback;
            ResponseEntity<String> response
                    = restTemplate.getForEntity(resourceUrl.trim(), String.class);
//            LOG.info((" GOT THE FOLLOWING QR CODE " + response.getBody()));

            Response challenge = context.form()
                    .setAttribute("qr", response.getBody())
                    .setAttribute("ssiSessionId", ssiSessionId)
                    .createForm("ssi-request.ftl");
            LOG.info("will respond with force challenge");
//force challenge means that it will not proceed to other authentication providers
            context.forceChallenge(challenge);
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }
    }

    @Override
    public void actionImpl(AuthenticationFlowContext afc) {
        LOG.info("before eidas actionImp called");
    }

}
