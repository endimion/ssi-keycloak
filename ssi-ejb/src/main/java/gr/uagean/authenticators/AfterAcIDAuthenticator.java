/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.authenticators;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.uaegean.pojo.MinEduΑacademicResponse;
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
public class AfterAcIDAuthenticator implements Authenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static Logger LOG = Logger.getLogger(AfterAcIDAuthenticator.class);

    private ObjectMapper mapper;
    private MemcachedClient mcc;

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        try {
            KeycloakSession session = context.getSession();
            RealmModel realm = context.getRealm();
            mapper = new ObjectMapper();

            LOG.info("reached after-AcId-authenticator!!!!!");

            String sessionId = context.getHttpRequest().getUri().getQueryParameters().getFirst("sessionId");
            LOG.info(sessionId);
            if (StringUtils.isEmpty(sessionId)) {
                context.attempted();
                return;
            }
            this.mcc = MemcacheSingleton.getCache();
            LOG.info("looking for: " + "user academicid details" + String.valueOf(sessionId));
            String minEduRespString = (String) this.mcc.get("details-" + String.valueOf(sessionId));
            LOG.info("GOT the following claims " + minEduRespString);

            ObjectMapper mapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MinEduΑacademicResponse minEduResp = mapper.readValue(minEduRespString, MinEduΑacademicResponse.class);

            // since we are not storing the users we use as a username the did
            UserModel user = KeycloakModelUtils.findUserByNameOrEmail(session, realm, minEduResp.getResult().getInspectionResult().getAcademicId());
            if (user == null) {
                // since we are not storing the users we use as a username the did
                user = session.users().addUser(realm, minEduResp.getResult().getInspectionResult().getAcademicId());
            }
            user.setEnabled(true);

            if (minEduResp.getResult().getInspectionResult() != null) {
                user.setFirstName(minEduResp.getResult().getInspectionResult().getLatinFirstName());
                user.setLastName(minEduResp.getResult().getInspectionResult().getLatinLastName());
                user.setEmail(minEduResp.getResult().getInspectionResult().getAcademicId() + "@academicId");
                user.setEmailVerified(true);
                user.setSingleAttribute("academicId-latinLastName", minEduResp.getResult().getInspectionResult().getLatinLastName());
                user.setSingleAttribute("academicId-latinFirstName", minEduResp.getResult().getInspectionResult().getLatinFirstName());
                user.setSingleAttribute("academicId-amka", minEduResp.getResult().getInspectionResult().getAmka());
                user.setSingleAttribute("academicId-academicId", minEduResp.getResult().getInspectionResult().getAcademicId());
                user.setSingleAttribute("academicId-residenceLocation", minEduResp.getResult().getInspectionResult().getResidenceLocation());
                user.setSingleAttribute("academicId-universityLocation", minEduResp.getResult().getInspectionResult().getUniversityLocation());
                user.setSingleAttribute("academicId-studentshipType", minEduResp.getResult().getInspectionResult().getStudentshipType());
                user.setSingleAttribute("academicId-greekFirstName", minEduResp.getResult().getInspectionResult().getGreekFirstName());
                user.setSingleAttribute("academicId-greekLastName", minEduResp.getResult().getInspectionResult().getGreekLastName());
                user.setSingleAttribute("academicId-departmentName", minEduResp.getResult().getInspectionResult().getDepartmentName());
                user.setSingleAttribute("academicId-entryYear", minEduResp.getResult().getInspectionResult().getEntryYear());
                user.setSingleAttribute("academicId-currentSemester", minEduResp.getResult().getInspectionResult().getCurrentSemester());
                user.setSingleAttribute("academicId-postGraduateProgram", minEduResp.getResult().getInspectionResult().getPostGraduateProgram());
                user.setSingleAttribute("academicId-pasoValidity", minEduResp.getResult().getInspectionResult().getPasoValidity());
                user.setSingleAttribute("academicId-pasoExpirationDate", minEduResp.getResult().getInspectionResult().getPasoExpirationDate());
                user.setSingleAttribute("academicId-submissionDate", minEduResp.getResult().getInspectionResult().getSubmissionDate());
                user.setSingleAttribute("academicId-applicationStatus", minEduResp.getResult().getInspectionResult().getApplicationStatus());
                user.setSingleAttribute("academicId-cancellationDate", minEduResp.getResult().getInspectionResult().getCancellationDate());
                user.setSingleAttribute("academicId-cancellationReason", minEduResp.getResult().getInspectionResult().getCancellationReason());
                user.setSingleAttribute("academicId-erasmus", minEduResp.getResult().getInspectionResult().getErasmus());
                user.setSingleAttribute("academicId-studentNumber", minEduResp.getResult().getInspectionResult().getStudentNumber());
                user.setSingleAttribute("academicId-photoUrl", minEduResp.getResult().getInspectionResult().getPhotoUrl());
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
