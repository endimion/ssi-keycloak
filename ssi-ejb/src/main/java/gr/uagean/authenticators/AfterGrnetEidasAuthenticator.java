/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.authenticators;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.uaegean.pojo.EidasGRnetUserResponse;
import gr.uaegean.pojo.GrnetTokenResponse;
import gr.uaegean.services.PropertiesService;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.codec.binary.Base64;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nikos
 */
public class AfterGrnetEidasAuthenticator implements Authenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static Logger LOG = LoggerFactory.getLogger(AfterGrnetEidasAuthenticator.class);
    private static Logger taxisLogger = LoggerFactory.getLogger("taxis");

    private ObjectMapper mapper;
    private MemcachedClient mcc;
    private PropertiesService propServ;

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        try {
            KeycloakSession session = context.getSession();
            RealmModel realm = context.getRealm();
            mapper = new ObjectMapper();
            LOG.info("reached grnet eidas-authenticator!!!!!");
            String sessionId = context.getHttpRequest().getUri().getQueryParameters().getFirst("sessionId");
            String code = context.getHttpRequest().getUri().getQueryParameters().getFirst("code");
            String plainIP = context.getHttpRequest().getUri().getQueryParameters().getFirst("ip");
            LOG.info("grnet eidas-authenticator sessionid:: " + sessionId + " code:: " + code);
            if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(code) || StringUtils.isEmpty(plainIP)) {
                context.attempted();
                return;
            }
            String ip = URLDecoder.decode(plainIP, StandardCharsets.UTF_8.name());
            //1************ retrieve from the code the accessToken
            RestTemplate restTemplate = new RestTemplate();
            this.propServ = new PropertiesService();
            final String grnetTokenEndpoint = this.propServ.getProp("GRNET_ACCESS_TOKEN_ENDPOINT");

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("grant_type", "authorization_code");
            map.add("client_id", this.propServ.getProp("GRNET_CLIENT_ID"));
            map.add("client_secret", this.propServ.getProp("GRNET_CLIENT_SECRET"));
            map.add("redirect_uri", this.propServ.getProp("GRNET_REDIRECT_URI"));

            HttpHeaders headers = createHeaders(this.propServ.getProp("GRNET_CLIENT_ID"), this.propServ.getProp("GRNET_CLIENT_SECRET"));
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            ResponseEntity<GrnetTokenResponse> accessToken = restTemplate.exchange(grnetTokenEndpoint,
                    HttpMethod.POST,
                    entity,
                    GrnetTokenResponse.class);

            LOG.info("GRNET TOKEN ENDPOINT RESPONSE!!!!");
            LOG.info(accessToken.getBody().toString());
            LOG.info("*****************");

            //2*******************retrieve with the access token the user attributes
            headers.clear();
            headers.set("Authorization", "Bearer " + accessToken.getBody().getToken());
            entity = new HttpEntity(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    this.propServ.getProp("GRNET_RESOURCE_OWNER_DETAILS"),
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            //3******************* parse the user attributes
            LOG.info("GRNET USER INFO RESPONSE!!!!");
            LOG.info(response.getBody());

            /*
            {"sub": "cc1a1a87e5d7d8a3a408756bd40144cf018aefb30f8b1ff212f51f564ae5272edff4920301c19419248478a0d9c3366c0aed693f4cd09c561331dc4b11685226@eid-proxy.aai-dev.grnet.gr",
            "given_name": "claude",
            "family_name": "Phil",
            "birthdate": "1965-01-01",
            "person_identifier": "GR/GR/11111"}
             */
            ObjectMapper mapper = new ObjectMapper();
            EidasGRnetUserResponse recievedUser = mapper.readValue(response.getBody(), EidasGRnetUserResponse.class);

            //4*******************set attributes on user, in session and proceed with authentication
            // since we are not storing the users we use as a username the did
            UserModel user = KeycloakModelUtils.findUserByNameOrEmail(session, realm, recievedUser.getPersonIdentnifier());
            if (user == null) {
                user = session.users().addUser(realm, recievedUser.getPersonIdentnifier());
            }
            user.setEnabled(true);

            if (user != null) {
                user.setFirstName(recievedUser.getGivenName());
                user.setLastName(recievedUser.getFamilyName());
                user.setEmail(recievedUser.getPersonIdentnifier() + "@eidas.gr.net");
                user.setEmailVerified(true);
                user.setSingleAttribute("DateOfBirth", recievedUser.getBirthdate());
                user.setSingleAttribute("FamilyName", recievedUser.getFamilyName());
                user.setSingleAttribute("PersonIdentifier", recievedUser.getPersonIdentnifier());
                user.setSingleAttribute("GivenName", recievedUser.getGivenName());
            }

            context.setUser(user);
            context.success();
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
            LOG.info("will continue with attempted");
            context.attempted();

        }
    }

    @Override
    public void action(AuthenticationFlowContext afc
    ) {
        LOG.info("AFTER eidas actionImp called");
        LOG.info(afc.getUser().toString());
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
    public boolean configuredFor(KeycloakSession ks, RealmModel rm,
            UserModel um
    ) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession ks, RealmModel rm,
            UserModel um
    ) {
    }

    @Override
    public void close() {

    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }

}
