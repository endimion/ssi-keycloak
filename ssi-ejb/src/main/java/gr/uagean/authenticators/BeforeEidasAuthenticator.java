/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.authenticators;

import gr.uaegean.pojo.Country;
import gr.uaegean.pojo.KeycloakSessionTO;
import gr.uaegean.singleton.MemcacheSingleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class BeforeEidasAuthenticator extends AbstractSSIAuthenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static final Logger LOG = LoggerFactory.getLogger(BeforeEidasAuthenticator.class);
    private MemcachedClient mcc;

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

            this.mcc = MemcacheSingleton.getCache();
            // grab oidc params
            String response_type = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.RESPONSE_TYPE);
            String client_id = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.CLIENT_ID);
            String redirect_uri = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.REDIRECT_URI);
            String state = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.STATE);
            String scope = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.SCOPE);
            String nonce = context.getHttpRequest().getUri().getQueryParameters().getFirst("nonce");
            String usersCountry = context.getHttpRequest().getUri().getQueryParameters().getFirst("country");

            int expiresInSec = 300;

            //Transfer Object that will be cached
            KeycloakSessionTO ksTO = new KeycloakSessionTO(state, response_type, client_id, redirect_uri, state, scope, "test", nonce);
            LOG.info("will add with key:" + state + " object " + ksTO.toString());
            mcc.add(state, expiresInSec, ksTO);

//
//TODO fix supported countries list
            List<Country> countries = new ArrayList<>();
            countries.add(new Country("Greece", "GR", true));
            countries.add(new Country("Spain", "ES", true));
            countries.add(new Country("Czech-Republic", "CZ", true));
            countries.add(new Country("Estonia", "EE", true));
            countries.add(new Country("Italy", "IT", true));
            countries.add(new Country("Lithuania", "LT", true));
            countries.add(new Country("Norway", "NO", true));
            countries.add(new Country("Germany", "DE", true));

            Response challenge = context.form()
                    .setAttribute("countries", countries)
                    .setAttribute("bakend", super.propServ.getProp("EIDAS_PROXY_SAML_ENDPOINT"))
                    .setAttribute("eidas", super.propServ.getProp("EIDAS_NODE_URI"))
                    .setAttribute("state", state) //keycloak-session
                    .setAttribute("country", usersCountry)
                    .createForm("wayf.ftl");
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
