/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.authenticators;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.uaegean.pojo.MinEduFamilyStatusResponse;
import gr.uaegean.pojo.MinEduFamilyStatusResponse.FamilyRecordsElement;
import gr.uaegean.singleton.MemcacheSingleton;
import java.io.IOException;
import net.spy.memcached.MemcachedClient;
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
public class AfterMitroAuthenticator implements Authenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static Logger LOG = LoggerFactory.getLogger(AfterMitroAuthenticator.class);

    private ObjectMapper mapper;
    private MemcachedClient mcc;

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        try {
            KeycloakSession session = context.getSession();
            RealmModel realm = context.getRealm();
            mapper = new ObjectMapper();

            LOG.info("reached after-Mitro-authenticator!!!!!");

            String sessionId = context.getHttpRequest().getUri().getQueryParameters().getFirst("sessionId");
            LOG.info(sessionId);
            if (StringUtils.isEmpty(sessionId)) {
                context.attempted();
                return;
            }
            this.mcc = MemcacheSingleton.getCache();
            LOG.info("looking for: " + "user Mitro details" + String.valueOf(sessionId));
            String minEduAmkaRespString = (String) this.mcc.get("mitro-" + String.valueOf(sessionId));

            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MinEduFamilyStatusResponse minEduFamResp = mapper.readValue(minEduAmkaRespString, MinEduFamilyStatusResponse.class);

            if (!minEduFamResp.isSuccess()) {
                throw new IOException("no record found in response, MinEduSuccess code in response was false");
            }

            // since we are not storing the users we use as a username the did
            String identifier = minEduFamResp.getResult().getRecords()[0].getRecords()[0].getEklspecialno();
            UserModel user = KeycloakModelUtils.findUserByNameOrEmail(session, realm, identifier);
            if (user == null) {
                // since we are not storing the users we use as a username the did
                user = session.users().addUser(realm, identifier);
            }
            user.setEnabled(true);

            if (minEduFamResp.getResult() != null && minEduFamResp.getResult().getRecords() != null && minEduFamResp.getResult().getRecords().length > 0) {

                FamilyRecordsElement record = minEduFamResp.getResult().getRecords()[0].getRecords()[0];

                user.setFirstName(record.getFirstname());
                user.setLastName(record.getSurname());
                user.setEmail(record.getEklspecialno() + "@mitro");
                user.setEmailVerified(true);
                user.setSingleAttribute("mitro-marriageactno", record.getMarriageactno());
                user.setSingleAttribute("mitro-familyShare", record.getFamilyShare());
                user.setSingleAttribute("mitro-firstname", record.getFirstname());
                user.setSingleAttribute("mitro-birthdate", record.getBirthdate());
                user.setSingleAttribute("mitro-gender", record.getGender());
                user.setSingleAttribute("mitro-spousemarriagerank", record.getSpousemarriagerank());
                user.setSingleAttribute("mitro-merida", record.getMerida());
                user.setSingleAttribute("mitro-membertype", record.getMembertype());
                user.setSingleAttribute("mitro-mansdecentraladmin", record.getMansdecentraladmin());
                user.setSingleAttribute("mitro-marriageactro", record.getMarriageactro());
                user.setSingleAttribute("mitro-birthmunicipalunit", record.getBirthmunicipalunit());
                user.setSingleAttribute("mitro-mansmunicipalityname", record.getMansmunicipalityname());
                user.setSingleAttribute("mitro-birthmunicipal", record.getBirthmunicipal());
                user.setSingleAttribute("mitro-eklspecialno", record.getEklspecialno());
                user.setSingleAttribute("mitro-motherfirstname", record.getMotherfirstname());
                user.setSingleAttribute("mitro-birthprefecture", record.getBirthprefecture());
                user.setSingleAttribute("mitro-surname", record.getSurname());
                user.setSingleAttribute("mitro-member", record.getMember());
                user.setSingleAttribute("mitro-marriageactdate", record.getMarriageactdate());
                user.setSingleAttribute("mitro-birthcountry", record.getBirthcountry());
                user.setSingleAttribute("mitro-reckind", record.getReckind());
                user.setSingleAttribute("mitro-mothersurname", record.getMothersurname());
                user.setSingleAttribute("mitro-mansrecordyear", record.getMansrecordyear());
                user.setSingleAttribute("mitro-maramunicipality", record.getMaramunicipality());
                user.setSingleAttribute("mitro-maramuniccomm", record.getMaramuniccomm());
                user.setSingleAttribute("mitro-secondname", record.getSecondname());
                user.setSingleAttribute("mitro-maracountry", record.getMaracountry());
                user.setSingleAttribute("mitro-mansreckind", record.getMansreckind());
                user.setSingleAttribute("mitro-marriagerank", record.getMarriagerank());
                user.setSingleAttribute("mitro-fathersurname", record.getFathersurname());
                user.setSingleAttribute("mitro-mainnationality", record.getMainnationality());
                user.setSingleAttribute("mitro-spouseagreementrank", record.getSpouseagreementrank());
                user.setSingleAttribute("mitro-fatherfirstname", record.getFatherfirstname());
                user.setSingleAttribute("mitro-marriageactyear", record.getMarriageactyear());
                user.setSingleAttribute("mitro-mothergenos", record.getMothergenos());
                user.setSingleAttribute("mitro-mansrecordaa", record.getMansrecordaa());
                user.setSingleAttribute("mitro-marriageacttomos", record.getMarriageacttomos());
                user.setSingleAttribute("mitro-municipalityname", record.getMunicipalityname());
                user.setSingleAttribute("mitro-maraprefecture", record.getMaraprefecture());
                user.setSingleAttribute("mitro-birthmuniccomm", record.getBirthmuniccomm());
                user.setSingleAttribute("mitro-gainmunrecdate", record.getGainmunrecdate());
                user.setSingleAttribute("mitro-grnatgaindate", record.getGrnatgaindate());
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
