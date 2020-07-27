package gr.uagean.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.uaegean.pojo.KeycloakSessionTO;
import gr.uaegean.pojo.MinEduFamilyStatusResponse;
import gr.uaegean.services.PropertiesService;
import gr.uaegean.singleton.MemcacheSingleton;
import gr.uaegean.singleton.MinEduSingleton;
import gr.uaegean.utils.RealmUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.spy.memcached.MemcachedClient;
import org.keycloak.OAuth2Constants;
import org.keycloak.models.KeycloakSession;
import org.keycloak.protocol.oidc.utils.OIDCRedirectUriBuilder;
import org.keycloak.protocol.oidc.utils.OIDCResponseMode;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MitroSpRestResource {

    private final KeycloakSession session;
    private static MinEduSingleton MINEDUSING;

    @SuppressWarnings("unused")
    private final AuthenticationManager.AuthResult auth;

    private PropertiesService propServ;

    private final static Logger LOG = LoggerFactory.getLogger(MitroSpRestResource.class);

    private MemcachedClient mcc;

    public MitroSpRestResource(KeycloakSession session) {
        this.session = session;
        this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());

        this.propServ = new PropertiesService();

    }

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param firstname
     * @param lastname
     * @param fathername
     * @param mothername
     * @param birthdate
     * @param sessionId the current sesionId of the user authentication
     * @return server error in case of errors, bad request if the user is not
     * found
     *
     * OR the sessionid in case of * correct retrieval of user attributes. In
     * this case the response is cached with key the sessionId so that it can be
     * propaged to the authentication succeded endpoint of keycloak
     *
     * @throws URISyntaxException
     * @throws JsonProcessingException
     * @throws IOException
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED + ";charset=UTF-8")
    @Path("mitro")
    public Response requestMitro(
            @Context HttpServletRequest httpServletRequest,
            @Context HttpServletResponse httpServletResponse,
            @FormParam("firstname") String firstname,
            @FormParam("lastname") String lastname,
            @FormParam("fathername") String fathername,
            @FormParam("mothername") String mothername,
            @FormParam("birthdate") String birthdate,
            @FormParam("sessionId") String sessionId) throws URISyntaxException, JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        httpServletRequest.setCharacterEncoding("UTF-8");
        firstname = new String(firstname.getBytes(), "UTF-8");
        LOG.info("requestMitro::  request for" + firstname + "and surname" + lastname + " with sessionId " + sessionId);

        String supervisorusername = this.propServ.getProp("SUPERVISOR_NAME");
        String supervisorpassword = this.propServ.getProp("SUPERVISOR_PASSWORD");
        String servicename = this.propServ.getProp("MITRO_SERVICE_NAME");

        Optional< MinEduFamilyStatusResponse> famStatus
                = MINEDUSING.getService().getFamilyStatusResponse(firstname, lastname, fathername, mothername, birthdate,
                        supervisorusername, supervisorpassword, servicename);
        if (famStatus.isPresent()) {
            MemcacheSingleton.getCache().add("mitro-" + sessionId, 1000, mapper.writeValueAsString(famStatus.get()));
            LOG.info("will cahce with key: " + sessionId);
            return Response.status(Response.Status.OK).entity(sessionId).build();
        }
        return Response.serverError().build();
    }

    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("proceed")
    public Response retriveOIDCParamAndProceed(@QueryParam("sessionId") String sessionId) throws IOException {
        LOG.info("proceed!!!");
        // see also keycloak OIDCLoginProtocolc lass method authenticated
        OIDCResponseMode responseMode = OIDCResponseMode.QUERY;
        String proceedWithAuthenticationUrl = this.propServ.getProp("MITRO_AUTH_PROCEED"); //http://localhost:8080/auth/realms/test/protocol/openid-connect/auth
        this.mcc = MemcacheSingleton.getCache();
        // retrieve from the cache the origianl OIDC call  paramters
        KeycloakSessionTO ksTo = (KeycloakSessionTO) mcc.get(sessionId);
        if (ksTo.getRealm() != null) {
            proceedWithAuthenticationUrl = RealmUtils.updateRealm(proceedWithAuthenticationUrl, ksTo.getRealm());
        }
        OIDCRedirectUriBuilder redirectUri = OIDCRedirectUriBuilder.fromUri(proceedWithAuthenticationUrl, responseMode);

        redirectUri.addParam(OAuth2Constants.RESPONSE_TYPE, ksTo.getResponseType());
        redirectUri.addParam(OAuth2Constants.CLIENT_ID, ksTo.getClientId());
        redirectUri.addParam(OAuth2Constants.REDIRECT_URI, ksTo.getClientRedirectUri());
        redirectUri.addParam(OAuth2Constants.STATE, ksTo.getState());
        redirectUri.addParam(OAuth2Constants.SCOPE, ksTo.getScope());
        redirectUri.addParam("sessionId", sessionId);
        LOG.info("proceed concluded ok");
        return redirectUri.build();
    }

}
