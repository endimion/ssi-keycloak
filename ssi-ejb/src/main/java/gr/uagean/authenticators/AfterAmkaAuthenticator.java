/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.authenticators;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.uaegean.pojo.MinEduAmkaResponse.AmkaResponse;
import gr.uaegean.singleton.MemcacheSingleton;
import java.io.IOException;
import net.spy.memcached.MemcachedClient;
import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.springframework.util.StringUtils;

/**
 *
 * @author nikos
 */
public class AfterAmkaAuthenticator implements Authenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static Logger LOG = Logger.getLogger(AfterAmkaAuthenticator.class);

    private ObjectMapper mapper;
    private MemcachedClient mcc;

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        try {
            KeycloakSession session = context.getSession();
            RealmModel realm = context.getRealm();
            mapper = new ObjectMapper();

            LOG.info("reached after-Amka-authenticator!!!!!");

            String sessionId = context.getHttpRequest().getUri().getQueryParameters().getFirst("sessionId");
            LOG.info(sessionId);
            if (StringUtils.isEmpty(sessionId)) {
                context.attempted();
                return;
            }
            this.mcc = MemcacheSingleton.getCache();
            LOG.info("looking for: " + "user amka details" + String.valueOf(sessionId));
            String minEduAmkaRespString = (String) this.mcc.get("amka-" + String.valueOf(sessionId));
            LOG.info("GOT the following amka details" + minEduAmkaRespString);

            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            AmkaResponse minEduAmkaResp = mapper.readValue(minEduAmkaRespString, AmkaResponse.class);
            LOG.info("Parsed to " + minEduAmkaResp.toString());
            // since we are not storing the users we use as a username the did
            UserModel user = KeycloakModelUtils.findUserByNameOrEmail(session, realm, minEduAmkaResp.getAmkaCurrent());
            if (user == null) {
                // since we are not storing the users we use as a username the did
                user = session.users().addUser(realm, minEduAmkaResp.getAmkaCurrent());
            }
            user.setEnabled(true);

            if (minEduAmkaResp != null) {
                user.setFirstName(minEduAmkaResp.getNameEn());
                user.setLastName(minEduAmkaResp.getSurnameCurEn());
                user.setEmail(minEduAmkaResp.getAmkaCurrent() + "@amka");
                user.setEmailVerified(true);
                user.setSingleAttribute("amka-latinLastName", minEduAmkaResp.getSurnameCurEn());
                user.setSingleAttribute("amka-latinFirstName", minEduAmkaResp.getNameEn());
                user.setSingleAttribute("amka-amka", minEduAmkaResp.getAmkaCurrent());
                user.setSingleAttribute("amka-birthMunicipalityGreekCode", minEduAmkaResp.getBirthMunicipalityGreekCode());
                user.setSingleAttribute("amka-birthDate", minEduAmkaResp.getBirthDate());
                user.setSingleAttribute("amka-fatherEN", minEduAmkaResp.getFatherEN());
                user.setSingleAttribute("amka-fatherGR", minEduAmkaResp.getFatherGR());
                user.setSingleAttribute("amka-tid", minEduAmkaResp.getTid());
                user.setSingleAttribute("amka-birthCountry", minEduAmkaResp.getBirthCountry());
                user.setSingleAttribute("amka-ssn", minEduAmkaResp.getSsn());
                user.setSingleAttribute("amka-birthCountryCode", minEduAmkaResp.getBirthCountryCode());
                user.setSingleAttribute("amka-lastModDate", minEduAmkaResp.getLastModDate());
                user.setSingleAttribute("amka-surnameCurGr", minEduAmkaResp.getSurnameCurGr());
                user.setSingleAttribute("amka-idType", minEduAmkaResp.getIdType());
                user.setSingleAttribute("amka-surnameCurEn", minEduAmkaResp.getSurnameCurEn());
                user.setSingleAttribute("amka-surnameBirthGr", minEduAmkaResp.getSurnameBirthGr());
                user.setSingleAttribute("amka-deathNote", minEduAmkaResp.getDeathNote());
                user.setSingleAttribute("amka-citizenship", minEduAmkaResp.getCitizenship());
                user.setSingleAttribute("amka-sex", minEduAmkaResp.getSex());
                user.setSingleAttribute("amka-surnameBirthEn", minEduAmkaResp.getSurnameBirthEn());
                user.setSingleAttribute("amka-match", minEduAmkaResp.getMatch());
                user.setSingleAttribute("amka-citizenshipCode", minEduAmkaResp.getCitizenshipCode());
                user.setSingleAttribute("amka-bdateIsTrue", minEduAmkaResp.getBdateIsTrue());
                user.setSingleAttribute("amka-birthMunicipality", minEduAmkaResp.getBirthMunicipality());
                user.setSingleAttribute("amka-deathDate", minEduAmkaResp.getDeathDate());
                user.setSingleAttribute("amka-amkaCurrent", minEduAmkaResp.getAmkaCurrent());
                user.setSingleAttribute("amka-motherEn", minEduAmkaResp.getMotherEn());
                user.setSingleAttribute("amka-motherGr", minEduAmkaResp.getMotherGr());
                user.setSingleAttribute("amka-idNum", minEduAmkaResp.getIdNum());
                user.setSingleAttribute("amka-nameGr", minEduAmkaResp.getNameGr());
                user.setSingleAttribute("amka-idIn", minEduAmkaResp.getIdIn());
                user.setSingleAttribute("amka-idCreationYear", minEduAmkaResp.getIdCreationYear());
                user.setSingleAttribute("amka-nameEn", minEduAmkaResp.getNameEn());
            }

            context.setUser(user);
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
        LOG.info(afc.getUser());
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
