package gr.uagean.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import gr.uaegean.pojo.KeycloakSessionTO;
import gr.uaegean.services.PropertiesService;
import gr.uaegean.singleton.MemcacheSingleton;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import org.springframework.util.StringUtils;

@ApplicationScoped
public class TaxisSpRestResource {

    private final KeycloakSession session;

    @SuppressWarnings("unused")
    private final AuthenticationManager.AuthResult auth;

    private PropertiesService propServ;

    private final static Logger LOG = LoggerFactory.getLogger(TaxisSpRestResource.class);

    private MemcachedClient mcc;

    public TaxisSpRestResource(KeycloakSession session) {
        this.session = session;
        this.auth = new AppAuthManager().authenticateBearerToken(session, session.getContext().getRealm());
        this.propServ = new PropertiesService();

    }

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param accessToken
     * @param <error>
     * @param hashedChallenge
     * @param taxisSessionId
     *
     */
    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("callback")
    public Response taxisCallback(
            @Context HttpServletRequest httpServletRequest,
            @Context HttpServletResponse httpServletResponse,
            @QueryParam("code") String code,
            @QueryParam("state") String sessionId
    ) throws URISyntaxException, JsonProcessingException, IOException {

        this.mcc = MemcacheSingleton.getCache();
        LOG.info("code" + code);
        LOG.info("state" + sessionId);
        KeycloakSessionTO ksTo = (KeycloakSessionTO) mcc.get(sessionId);
        OIDCResponseMode responseMode = OIDCResponseMode.QUERY;
        String proceedWithAuthenticationUrl = this.propServ.getProp("TAXIS_AUTH_PROCEED"); //https://dss1.aegean.gr/auth/realms/SSI/protocol/openid-connect/auth
        LOG.info("will proceed to " + proceedWithAuthenticationUrl);
        OIDCRedirectUriBuilder redirectUri = OIDCRedirectUriBuilder.fromUri(proceedWithAuthenticationUrl, responseMode);
        redirectUri.addParam(OAuth2Constants.RESPONSE_TYPE, ksTo.getResponseType());
        redirectUri.addParam(OAuth2Constants.CLIENT_ID, ksTo.getClientId());
        redirectUri.addParam(OAuth2Constants.REDIRECT_URI, ksTo.getClientRedirectUri());
        redirectUri.addParam(OAuth2Constants.STATE, ksTo.getState());
        redirectUri.addParam(OAuth2Constants.SCOPE, ksTo.getScope());
        if (!StringUtils.isEmpty(ksTo.getNonce())) {
            redirectUri.addParam("nonce", ksTo.getNonce());
        }
        redirectUri.addParam("sessionId", sessionId);
        redirectUri.addParam("code", code);
        redirectUri.addParam("ip", URLEncoder.encode(httpServletRequest.getRemoteAddr(), StandardCharsets.UTF_8.toString()));
        LOG.info("Proceeding ");
        return redirectUri.build();
    }

}
