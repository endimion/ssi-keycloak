/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.authenticators;

import com.google.common.hash.Hashing;
import gr.uaegean.pojo.KeycloakSessionTO;
import gr.uaegean.services.PropertiesService;
import gr.uaegean.singleton.MemcacheSingleton;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.ws.rs.core.Response;
import net.spy.memcached.MemcachedClient;
import org.keycloak.OAuth2Constants;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nikos
 */
public class BeforeGrnetEidasAuthenticator extends AbstractTaxisAuthenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static final Logger LOG = LoggerFactory.getLogger(BeforeGrnetEidasAuthenticator.class);
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
//            KeycloakSession session = context.getSession();
//            RealmModel realm = context.getRealm();
            this.mcc = MemcacheSingleton.getCache();
            this.propServ = new PropertiesService();
            // create a new sessionId
            String sessionId = UUID.randomUUID().toString();
            // grab oidc params
            String response_type = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.RESPONSE_TYPE);
            String client_id = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.CLIENT_ID);
            String redirect_uri = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.REDIRECT_URI);
            String state = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.STATE);
            String scope = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.SCOPE);
            String nonce = context.getHttpRequest().getUri().getQueryParameters().getFirst("nonce");
            int expiresInSec = 300;
            //Transfer Object that will be cached
            KeycloakSessionTO ksTO = new KeycloakSessionTO(state, response_type,
                    client_id, redirect_uri, state, scope, null, nonce);
            mcc.add(sessionId, expiresInSec, ksTO);
            // storing the hash of the sessionId as that will be sent back after the auathentication (as the session with the taxis service)
            // this way the actuall sessionId can be retrieved from the received hash
            String hahsedChallenge = Hashing.sha256()
                    .hashString(sessionId, StandardCharsets.UTF_8)
                    .toString();
            LOG.info("BeforeGrnetEidasAuthenticator:: will store the hashedChallenge:: " + hahsedChallenge);
            mcc.add(hahsedChallenge, expiresInSec, sessionId);

            Response challenge = context.form()
                    .setAttribute("clientId", this.propServ.getProp("GRNET_CLIENT_ID"))
                    .setAttribute("redirectURI", this.propServ.getProp("GRNET_REDIRECT_URI"))
                    .setAttribute("authorizeURI", this.propServ.getProp("GRNET_OAUTH2_URL_AUTHORIZE"))
                    .setAttribute("state", sessionId)
                    .createForm("grnet.ftl");
            LOG.info("will respond with force challenge");
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
