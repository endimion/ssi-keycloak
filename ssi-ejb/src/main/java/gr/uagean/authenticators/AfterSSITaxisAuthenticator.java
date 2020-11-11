/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.authenticators;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.uaegean.pojo.VerifiableCredential;
import gr.uaegean.singleton.MemcacheSingleton;
import static gr.uaegean.utils.CredentialsUtils.getClaimsFromVerifiedArray;
import java.io.IOException;
import net.spy.memcached.MemcachedClient;
import org.keycloak.OAuth2Constants;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 *
 * @author nikos
 */
public class AfterSSITaxisAuthenticator implements Authenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static Logger LOG = LoggerFactory.getLogger(AfterSSITaxisAuthenticator.class);

    private ObjectMapper mapper;
    private MemcachedClient mcc;

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        try {
            KeycloakSession session = context.getSession();
            RealmModel realm = context.getRealm();
            mapper = new ObjectMapper();

            LOG.info("reached after-SSI-TAXIS-authenticator!!!!!");

            String sessionId = context.getHttpRequest().getUri().getQueryParameters().getFirst("sessionId");
            LOG.info(sessionId);
            if (StringUtils.isEmpty(sessionId)) {
                LOG.info("no  seessionId found!!!!!!! AFTERSSIAuthenticator");
                LOG.info("will continue with attempted");
                context.attempted();
                return;
            }

            this.mcc = MemcacheSingleton.getCache();
            LOG.info("looking for: " + "claims" + String.valueOf(sessionId));
            String claims = (String) this.mcc.get("claims" + String.valueOf(sessionId));
            LOG.info("GOT the following SSI claims " + claims);

            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            VerifiableCredential credential = mapper.readValue(claims, VerifiableCredential.class);
            VerifiableCredential.VerifiedClaims vc = getClaimsFromVerifiedArray(credential);

            // since we are not storing the users we use as a username the did
            UserModel user = KeycloakModelUtils.findUserByNameOrEmail(session, realm, credential.getDid());
            if (user == null) {
                // since we are not storing the users we use as a username the did
                user = session.users().addUser(realm, credential.getDid());
            }
            user.setEnabled(true);

            if (vc.getTaxisRoute() != null && vc.getTaxisRoute().getTaxis() != null) {
                user.setFirstName(vc.getTaxisRoute().getTaxis().getFirstNameLatin());
                user.setLastName(vc.getTaxisRoute().getTaxis().getLastNameLatin());
                user.setEmail(credential.getDid() + "@uport");
                user.setEmailVerified(true);

                LOG.info("THE VC OBJECT IS");
                LOG.info(vc.toString());
                user.setSingleAttribute("taxis-familyName", vc.getTaxisRoute().getTaxis().getLastName());
                user.setSingleAttribute("taxis-firstName", vc.getTaxisRoute().getTaxis().getFistName());
                user.setSingleAttribute("taxis-firstNameLatin", vc.getTaxisRoute().getTaxis().getFirstNameLatin());
                user.setSingleAttribute("taxis-familyNameLatin", vc.getTaxisRoute().getTaxis().getLastNameLatin());
                user.setSingleAttribute("taxis-afm", vc.getTaxisRoute().getTaxis().getAfm());
                user.setSingleAttribute("taxis-amka", vc.getTaxisRoute().getTaxis().getAmka());
                user.setSingleAttribute("taxis-fathersName", vc.getTaxisRoute().getTaxis().getFathersName());
                user.setSingleAttribute("taxis-fathersNameLatin", vc.getTaxisRoute().getTaxis().getFathersNameLatin());
                user.setSingleAttribute("taxis-mothersName", vc.getTaxisRoute().getTaxis().getMothersName());
                user.setSingleAttribute("taxis-mothersNameLatin", vc.getTaxisRoute().getTaxis().getMothersNameLatin());
                user.setSingleAttribute("taxis-source", vc.getTaxisRoute().getTaxis().getSource());
                user.setSingleAttribute("taxis-dateOfBirth", vc.getTaxisRoute().getTaxis().getDateOfBirth());
                user.setSingleAttribute("taxis-gender", vc.getTaxisRoute().getTaxis().getGender());
                user.setSingleAttribute("taxis-nationality", vc.getTaxisRoute().getTaxis().getNationality());
//                user.setSingleAttribute("taxis-household", mapper.writeValueAsString(vc.getTaxisRoute().getTaxis().getHousehold()));
                if (vc.getTaxisRoute() != null && vc.getTaxisRoute().getTaxis() != null
                        && vc.getTaxisRoute().getTaxis().getAddress() != null) {
                    user.setSingleAttribute("taxis-address-street", vc.getTaxisRoute().getTaxis().getAddress().getStreet());
                    user.setSingleAttribute("taxis-address-number", vc.getTaxisRoute().getTaxis().getAddress().getStreetNumber());
                    user.setSingleAttribute("taxis-address-po", vc.getTaxisRoute().getTaxis().getAddress().getPo());
                    user.setSingleAttribute("taxis-address-prefecture", vc.getTaxisRoute().getTaxis().getAddress().getPrefecture());
                    user.setSingleAttribute("taxis-address-municipality", vc.getTaxisRoute().getTaxis().getAddress().getMunicipality());
                }

                user.setSingleAttribute("taxis-credential-id", vc.getId());
                user.setSingleAttribute("iat", credential.getVerified()[0].getIat());
                user.setSingleAttribute("exp", credential.getVerified()[0].getExp());
                user.setSingleAttribute("credential-name", "Taxis");

            }

            if (vc.getAmka() != null && vc.getAmka().getAmka() != null) {
                if (user.getFirstName() == null) {
                    user.setFirstName(vc.getAmka().getAmka().getLatinFirstName());
                }
                if (user.getLastName() == null) {
                    user.setLastName(vc.getAmka().getAmka().getLatinLastName());
                }
                if (user.getEmail() == null) {
                    user.setEmail(vc.getAmka().getAmka().getBirthDate() + "-" + vc.getAmka().getAmka().getLatinLastName() + "@uport");
                }
                user.setEmailVerified(true);
                user.setSingleAttribute("amka-latinLastName", vc.getAmka().getAmka().getLatinLastName());
                user.setSingleAttribute("amka-latinFirstName", vc.getAmka().getAmka().getLatinFirstName());
                user.setSingleAttribute("amka-birthDate", vc.getAmka().getAmka().getBirthDate());
                user.setSingleAttribute("amka-father", vc.getAmka().getAmka().getFather());
                user.setSingleAttribute("amka-mother", vc.getAmka().getAmka().getMother());
                user.setSingleAttribute("amka-mother", vc.getAmka().getAmka().getMother());
                user.setSingleAttribute("amka-loa", vc.getAmka().getAmka().getLoa());
            }

            if (vc.getMitro() != null && vc.getMitro().getMitro() != null) {
                user.setSingleAttribute("mitro-gender", vc.getMitro().getMitro().getGender());
                user.setSingleAttribute("mitro-nationality", vc.getMitro().getMitro().getNationality());
                user.setSingleAttribute("mitro-maritalStatus", vc.getMitro().getMitro().getMaritalStatus());
            }

            if (vc.getErasmus() != null && vc.getErasmus().getMitro() != null) {
                user.setSingleAttribute("eidas-familyName", vc.getErasmus().getMitro().getFamilyName());
                user.setSingleAttribute("eidas-firstName", vc.getErasmus().getMitro().getGivenName());
                user.setSingleAttribute("eidas-dateOfBirth", vc.getErasmus().getMitro().getDateOfBirth());
                user.setSingleAttribute("eidas-personIdentifier", vc.getErasmus().getMitro().getPersonIdentifier());
                user.setSingleAttribute("eidas-loa", vc.getErasmus().getMitro().getLoa());
                user.setSingleAttribute("erasmus-expires", vc.getErasmus().getMitro().getExpires());
                user.setSingleAttribute("erasmus-hostingInstitution", vc.getErasmus().getMitro().getHostingInstitution());
                user.setSingleAttribute("erasmus-affiliation", vc.getErasmus().getMitro().getAffiliation());
            }

            // grab oidc params
            String response_type = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.RESPONSE_TYPE);
            String client_id = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.CLIENT_ID);
            String redirect_uri = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.REDIRECT_URI);
            String state = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.STATE);
            String scope = context.getHttpRequest().getUri().getQueryParameters().getFirst(OAuth2Constants.SCOPE);
            //DEbug ensure we are getting the correct parameaters here
            LOG.info("AFTER SSI PERSONAL Authenticator parameters!!!");
            LOG.info(response_type);
            LOG.info(client_id);
            LOG.info(redirect_uri);
            LOG.info(state);
            LOG.info(scope);

            context.setUser(user);
            LOG.info("AfterSSIAuthenticator Success!! user is set " + user.getUsername());

            context.success();
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
            //TODO failure is better?
            LOG.info("will continue with attempted");
            context.attempted();
        }
    }

    @Override
    public void action(AuthenticationFlowContext afc) {
        LOG.info("AFTER eidas actionImp called");
//        LOG.info(afc.getUser());
        if (afc.getUser() != null) {
            afc.success();
        } else {
            afc.attempted();
        }
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

}
