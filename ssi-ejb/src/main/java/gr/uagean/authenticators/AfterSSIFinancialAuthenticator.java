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
public class AfterSSIFinancialAuthenticator implements Authenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static Logger LOG = LoggerFactory.getLogger(AfterSSIFinancialAuthenticator.class);

    private ObjectMapper mapper;
    private MemcachedClient mcc;

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        try {
            KeycloakSession session = context.getSession();
            RealmModel realm = context.getRealm();
            mapper = new ObjectMapper();

            LOG.info("reached after-SSI-authenticator!!!!!");

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
            VerifiableCredential vc = mapper.readValue(claims, VerifiableCredential.class);

            // since we are not storing the users we use as a username the did
            UserModel user = KeycloakModelUtils.findUserByNameOrEmail(session, realm, vc.getDid());
            if (user == null) {
                // since we are not storing the users we use as a username the did
                user = session.users().addUser(realm, vc.getDid());
            }
            user.setEnabled(true);

            //TODO Add other data sources!!!
            if (vc.getE1() != null && vc.getE1().getE1() != null) {
                user.setFirstName(vc.getE1().getE1().getName());
                user.setLastName(vc.getE1().getE1().getSurname());
                user.setEmail(vc.getDid() + "@uport");
                user.setEmailVerified(true);

                user.setSingleAttribute("e1-salaries", vc.getE1().getE1().getSalaries());
                user.setSingleAttribute("e1-pensionIncome", vc.getE1().getE1().getPensionIncome());
                user.setSingleAttribute("e1-farmingActivity", vc.getE1().getE1().getFarmingActivity());
                user.setSingleAttribute("e1-freelanceActivity", vc.getE1().getE1().getFreelanceActivity());
                user.setSingleAttribute("e1-rentIncome", vc.getE1().getE1().getRentIncome());
                user.setSingleAttribute("e1-unemploymentBenefit", vc.getE1().getE1().getUnemploymentBenefit());
                user.setSingleAttribute("e1-otherBenefitsIncome", vc.getE1().getE1().getOtherBenefitsIncome());
                user.setSingleAttribute("e1-ekas", vc.getE1().getE1().getEkas());
                user.setSingleAttribute("e1-additionalIncomes", vc.getE1().getE1().getAdditionalIncomes());
                user.setSingleAttribute("e1-ergome", vc.getE1().getE1().getErgome());
                user.setSingleAttribute("e1-depositInterest", vc.getE1().getE1().getDepositInterest());
                user.setSingleAttribute("e1-deposits", vc.getE1().getE1().getDeposits());
                user.setSingleAttribute("e1-valueOfRealEstate", vc.getE1().getE1().getValueOfRealEstate());
                user.setSingleAttribute("e1-valueOfRealEstateInOtherCountries", vc.getE1().getE1().getValueOfRealEstateInOtherCountries());
                user.setSingleAttribute("e1-valueOfOwnedVehicles", vc.getE1().getE1().getValueOfOwnedVehicles());
                user.setSingleAttribute("e1-investments", vc.getE1().getE1().getInvestments());
                user.setSingleAttribute("e1-householdComposition", vc.getE1().getE1().getHouseholdComposition());
                user.setSingleAttribute("e1-name", vc.getE1().getE1().getName());
                user.setSingleAttribute("e1-surname", vc.getE1().getE1().getSurname());
                user.setSingleAttribute("e1-dateOfBirth", vc.getE1().getE1().getDateOfBirth());
                user.setSingleAttribute("e1-municipality", vc.getE1().getE1().getMunicipality());
                user.setSingleAttribute("e1-number", vc.getE1().getE1().getNumber());
                user.setSingleAttribute("e1-po", vc.getE1().getE1().getPo());
                user.setSingleAttribute("e1-prefecture", vc.getE1().getE1().getPrefecture());
                user.setSingleAttribute("e1-street", vc.getE1().getE1().getStreet());

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
