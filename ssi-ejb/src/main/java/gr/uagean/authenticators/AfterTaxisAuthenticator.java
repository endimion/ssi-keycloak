/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uagean.authenticators;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.uaegean.pojo.TaxisTokenResponse;
import gr.uaegean.pojo.TaxisUserResponse;
import gr.uaegean.services.PropertiesService;
import gr.uaegean.utils.TaxisRequestLogFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import net.spy.memcached.MemcachedClient;
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
public class AfterTaxisAuthenticator implements Authenticator {

//    protected ParameterService paramServ = new ParameterServiceImpl();
    private static Logger LOG = LoggerFactory.getLogger(AfterTaxisAuthenticator.class);
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
            LOG.info("reached after-Taxis-authenticator!!!!!");
            String sessionId = context.getHttpRequest().getUri().getQueryParameters().getFirst("sessionId");
            String code = context.getHttpRequest().getUri().getQueryParameters().getFirst("code");
            String plainIP = context.getHttpRequest().getUri().getQueryParameters().getFirst("ip");
            LOG.info("After-taxis sessionId received:: " + sessionId + " code:: " + code);
            if (StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(code) || StringUtils.isEmpty(plainIP)) {
                context.attempted();
                return;
            }
            String ip = URLDecoder.decode(plainIP, StandardCharsets.UTF_8.name());
            //1************ retrieve from the code the accessToken
            RestTemplate restTemplate = new RestTemplate();
            this.propServ = new PropertiesService();
            final String taxisTokenEndpoint = this.propServ.getProp("TAXIS_ACCESS_TOKEN_ENDPOINT");
            taxisLogger.info(TaxisRequestLogFactory.build(taxisTokenEndpoint, "POST", ip).toString());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("grant_type", "authorization_code");
            map.add("client_id", this.propServ.getProp("TAXIS_CLIENT_ID"));
            map.add("client_secret", this.propServ.getProp("TAXIS_CLIENT_SECRET"));
            map.add("redirect_uri", this.propServ.getProp("TAXIS_REDIRECT_URI"));
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<TaxisTokenResponse> accessToken = restTemplate.exchange(taxisTokenEndpoint,
                    HttpMethod.POST,
                    entity,
                    TaxisTokenResponse.class);
            //2*******************retrieve with the access token the user attributes
            taxisLogger.info(TaxisRequestLogFactory.build(this.propServ.getProp("TAXIS_URL_RESOURCE_OWNER_DETAILS"), "GET", ip).toString());
            LOG.error("TESTing errror log");
            headers.clear();
            headers.set("Authorization", "Bearer " + accessToken.getBody().getToken());
            entity = new HttpEntity(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    this.propServ.getProp("TAXIS_URL_RESOURCE_OWNER_DETAILS"),
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            //3******************* parse the user attributes
            LOG.info(response.getBody());
            JAXBContext marshaller = JAXBContext.newInstance(TaxisUserResponse.class);
            Unmarshaller unmarshaller = marshaller.createUnmarshaller();
            TaxisUserResponse userResponse = (TaxisUserResponse) unmarshaller.unmarshal(new StringReader(response.getBody()));

            //4*******************set attributes on user, in session and proceed with authentication
            // since we are not storing the users we use as a username the did
            UserModel user = KeycloakModelUtils.findUserByNameOrEmail(session, realm, userResponse.getUserInfo().getTaxid());
            if (user == null) {
                user = session.users().addUser(realm, userResponse.getUserInfo().getTaxid());
            }
            user.setEnabled(true);

            if (userResponse.getUserInfo() != null) {
                user.setFirstName(userResponse.getUserInfo().getFirstname());
                user.setLastName(userResponse.getUserInfo().getLastname());
                user.setEmail(userResponse.getUserInfo().getTaxid() + "@taxis");
                user.setEmailVerified(true);
                user.setSingleAttribute("taxis-birthyear", userResponse.getUserInfo().getBirthyear());
                user.setSingleAttribute("taxis-fathername", userResponse.getUserInfo().getFathername());
                user.setSingleAttribute("taxis-firstname", userResponse.getUserInfo().getFirstname());
                user.setSingleAttribute("taxis-lastname", userResponse.getUserInfo().getLastname());
                user.setSingleAttribute("taxis-mothername", userResponse.getUserInfo().getMothername());
                user.setSingleAttribute("taxis-taxid", userResponse.getUserInfo().getTaxid());
                user.setSingleAttribute("taxis-userid", userResponse.getUserInfo().getUserid());
            }

            context.setUser(user);
            context.success();
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
            LOG.info("will continue with attempted");
            context.attempted();

        } catch (JAXBException ex) {
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

}
