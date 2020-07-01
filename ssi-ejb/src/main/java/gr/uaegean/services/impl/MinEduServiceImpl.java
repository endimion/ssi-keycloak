/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uaegean.services.impl;

import gr.uaegean.pojo.GrantRequest;
import gr.uaegean.pojo.MinEduAmkaResponse;
import gr.uaegean.pojo.MinEduAmkaResponse.AmkaResponse;
import gr.uaegean.pojo.MinEduCheckByAmkaResponse;
import gr.uaegean.pojo.MinEduFamilyStatusResponse;
import gr.uaegean.pojo.MinEduLog;
import gr.uaegean.pojo.MinEduResponse;
import gr.uaegean.pojo.MinEduResponse.InspectionResult;
import gr.uaegean.pojo.MinEduΑacademicResponse;
import gr.uaegean.pojo.TokenResponse;
import gr.uaegean.pojo.TokenResponse.OAuths;
import gr.uaegean.services.MinEduService;
import gr.uaegean.services.PropertiesService;
import gr.uaegean.utils.TimestampUtils;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nikos
 */
public class MinEduServiceImpl implements MinEduService {

    private PropertiesService paramServ;
    private final String minEduTokenUri;
    private final String minEduTokenUser;
    private final String minEduTokenPass;
    private final String minEduTokenGrantType;
    private final String minEduAmkaQueryEndpoint;
    private final String minEduMitroPolitonEndpoint;
    private final String minEduQueryByAmkaEndpoint;
    private LocalDateTime accessTokenExpiration;
    private Optional<String> activeToken;
    private final String minEduQueryIdEndpoint;

    private final static Logger LOG = LoggerFactory.getLogger(MinEduServiceImpl.class);

    public MinEduServiceImpl(PropertiesService paramServ) {
        this.paramServ = paramServ;
        this.minEduTokenUri = paramServ.getProp("MINEDU_TOKEN_URL");
        this.minEduTokenPass = paramServ.getProp("MINEDU_TOKEN_PASSWORD");
        this.minEduTokenUser = paramServ.getProp("MINEDU_TOKEN_USERNAME");
        this.minEduTokenGrantType = paramServ.getProp("MINEDU_TOKEN_GRANTTYPE");
        this.minEduAmkaQueryEndpoint = paramServ.getProp("MINEDU_QUERY_AMKA_URL");
        this.minEduMitroPolitonEndpoint = paramServ.getProp("MINEDU_QUERY_MITRO_URL");
        this.minEduQueryByAmkaEndpoint = paramServ.getProp("MINEDU_QUERY_BY_AMKA"); //https://gateway.interoperability.gr/academicId/1.0.1/student/
        this.minEduQueryIdEndpoint = paramServ.getProp("MINEDU_QUERYID_URL");
        this.accessTokenExpiration = LocalDateTime.now();
        this.activeToken = Optional.empty();
    }

    @Override
    public Optional<String> getAccessTokenByTokenType(String tokenType) throws HttpClientErrorException {
        GrantRequest grantReq = new GrantRequest(minEduTokenUser, minEduTokenPass, minEduTokenGrantType);
        RestTemplate restTemplate = new RestTemplate();
        LOG.info("will get toke from theurl: " + minEduTokenUri + " for token Type " + tokenType);
        try {
            if (activeToken.isPresent() && accessTokenExpiration.isAfter(LocalDateTime.now().plusSeconds(30))) {
                LOG.info("MinEdu OAth token still alive " + activeToken.get());
                return activeToken;
            } else {
                LOG.info("will get new token ");
                TokenResponse tokResp = restTemplate.postForObject(minEduTokenUri, grantReq, TokenResponse.class);
                if (tokResp != null && tokResp.getSuccess().equals("true") && tokResp.getOauths() != null) {
                    Optional<OAuths> oAuth = Arrays.stream(tokResp.getOauths()).filter(token -> {
                        return token.getTokenTypeEN().equals(tokenType);
                    }).findFirst();
                    if (oAuth.isPresent()) {
                        this.accessTokenExpiration = this.accessTokenExpiration.plusSeconds(oAuth.get().getOauth().getExpiresIn());
                        this.activeToken = Optional.of(oAuth.get().getOauth().getAccessToken());
                        return this.activeToken;
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            LOG.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getAccessToken(String type) {
        GrantRequest grantReq = new GrantRequest(minEduTokenUser, minEduTokenPass, minEduTokenGrantType);
        RestTemplate restTemplate = new RestTemplate();
        LOG.info("will get toke from theurl: " + minEduTokenUri);
        try {
            if (activeToken.isPresent() && accessTokenExpiration.isAfter(LocalDateTime.now().plusSeconds(30))) {
                LOG.info("MinEdu OAth token still alive " + activeToken.get());
                return activeToken;
            } else {
                LOG.info("will get new token for " + type);
                TokenResponse tokResp = restTemplate.postForObject(minEduTokenUri, grantReq, TokenResponse.class);

                Optional<OAuths> mitroToken = Arrays.stream(tokResp.getOauths()).filter(token -> {
                    LOG.info(token.getTokenType());
                    return token.getTokenType().equals(type);
                }).findFirst();
                LOG.info("token:: " + mitroToken.get().getOauth().getAccessToken());

                if (tokResp != null && tokResp.getSuccess().equals("true") && mitroToken.isPresent()) {
//                    LOG.info("retrieved token " + tokResp.getOauths()[0].getOauth().getAccessToken());
                    this.accessTokenExpiration = this.accessTokenExpiration.plusSeconds(mitroToken.get().getOauth().getExpiresIn());
                    this.activeToken = Optional.of(mitroToken.get().getOauth().getAccessToken());
                    return this.activeToken;
                }
            }
        } catch (HttpClientErrorException e) {
            LOG.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<AmkaResponse> getAmkaFullResponse(String amka, String surname) {
        String minEduQueryIdUrl = this.minEduAmkaQueryEndpoint + "/" + amka + "/" + surname + "/extended";
        HttpHeaders requestHeaders = new HttpHeaders();
        Optional<String> accessToken = getAccessToken("Δεδομένα ΑΜΚΑ API");
        if (accessToken.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            requestHeaders.add("Authorization", "Bearer " + accessToken.get());
            HttpEntity<?> entity = new HttpEntity<>(requestHeaders);
            LOG.info("querying for amka " + amka + "with surname " + surname);
            try {
                ResponseEntity<MinEduAmkaResponse> amkaResponseEntity = restTemplate.exchange(minEduQueryIdUrl, HttpMethod.GET, entity, MinEduAmkaResponse.class);
                MinEduAmkaResponse amkaResp = amkaResponseEntity.getBody();
                return Optional.of(amkaResp.getResult());
            } catch (HttpClientErrorException e) {
                LOG.error(e.getMessage());
            }
        }
        LOG.error("no token found in response!");
        return Optional.empty();
    }

    @Override
    public Optional<MinEduΑacademicResponse.InspectionResult> getInspectioResultByAcademicId(String academicId, String selectedUniversityId, String esmoSessionId) throws HttpClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<String> getAcademicIdFromAMKA(String amkaNumber, String selectedUniversityId, String esmoSessionId) {
        String requestUrl = this.minEduQueryByAmkaEndpoint + "/" + amkaNumber + "?fields=academicID&username=" + this.minEduTokenUser + "&password=" + this.minEduTokenPass;
        HttpHeaders requestHeaders = new HttpHeaders();
        Optional<String> accessToken = getAccessToken("Δεδομένα Ακαδημαικής Ταυτότητας API");
        if (accessToken.isPresent()) {
            LOG.info("querying for amka " + amkaNumber);
            LOG.info("will query amka in theurl: " + requestUrl);
            RestTemplate restTemplate = new RestTemplate();
            requestHeaders.add("Authorization", "Bearer " + accessToken.get());
            HttpEntity<?> entity = new HttpEntity<>(requestHeaders);
            try {
                ResponseEntity<MinEduCheckByAmkaResponse> queryResponse = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, MinEduCheckByAmkaResponse.class);
                LOG.info("result " + queryResponse.getBody().getResult().getAcademicID());
                MinEduLog logEntry = new MinEduLog(queryResponse.getBody().getServiceCallID(), selectedUniversityId, queryResponse.getBody().getTimestamp(), TimestampUtils.getIso8601Date(), esmoSessionId);
                LOG.info("MinEduLog " + logEntry.toString());

                if (queryResponse.getBody().isSuccess()) {
                    if (queryResponse.getBody().getResult() != null && queryResponse.getBody().getResult().getAcademicID() != null) {
                        return Optional.of(queryResponse.getBody().getResult().getAcademicID());
                    }
                    LOG.error("no acadmic id found for amka " + amkaNumber);
                }
            } catch (HttpClientErrorException e) {
                LOG.error(e.getMessage());
            }
        }
        LOG.error("no token found in response!");
        return Optional.empty();
    }

    @Override
    public Optional<InspectionResult> getInspectioResponseByAcademicId(String academicId, String selectedUniversityId, String esmoSessionId) throws HttpClientErrorException {
        String minEduQueryIdUrl = this.minEduQueryIdEndpoint + "?id=" + academicId + "&username=" + this.minEduTokenUser + "&password=" + this.minEduTokenPass;
        HttpHeaders requestHeaders = new HttpHeaders();
        Optional<String> accessToken = getAccessToken("Δεδομένα Ακαδημαικής Ταυτότητας API");
        if (accessToken.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            requestHeaders.add("Authorization", "Bearer " + accessToken.get());
            HttpEntity<?> entity = new HttpEntity<>(requestHeaders);
            LOG.info("querying for academicId " + academicId);
            try {
                ResponseEntity<MinEduResponse> queryId = restTemplate.exchange(minEduQueryIdUrl, HttpMethod.GET, entity, MinEduResponse.class);
                MinEduResponse qResp = queryId.getBody();
                InspectionResult ir = qResp.getResult().getInspectionResult();
                MinEduLog logEntry = new MinEduLog(qResp.getServiceCallID(), selectedUniversityId, qResp.getTimestamp(), TimestampUtils.getIso8601Date(), esmoSessionId);
                LOG.info("MinEduLog " + logEntry.toString());
                return Optional.of(ir);
            } catch (HttpClientErrorException e) {
                LOG.error(e.getMessage());
            }

        }
        LOG.error("no token found in response!");
        return Optional.empty();
    }

    @Override
    public Optional<MinEduFamilyStatusResponse> getFamilyStatusResponse(String firstname, String lastname,
            String fathername, String mothername, String birthdate, String supervisorusername,
            String supervisorpassword, String servicename) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(this.minEduMitroPolitonEndpoint)
                .append(firstname).append("/")
                .append(lastname).append("/")
                .append(fathername).append("/")
                .append(mothername).append("/")
                .append(birthdate).append("?")
                .append("supervisorusername=").append(supervisorusername).append("&")
                .append("supervisorpassword=").append(supervisorpassword).append("&")
                .append("servicename=").append("Βεβαίωση Οικογενειακής Κατάστασης");
        String minEduQueryIdUrl = urlBuilder.toString();
        HttpHeaders requestHeaders = new HttpHeaders();
        Optional<String> accessToken = getAccessToken("Δεδομένα Μητρώου Πολιτών");
        if (accessToken.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            requestHeaders.add("Authorization", "Bearer " + accessToken.get());
            HttpEntity<?> entity = new HttpEntity<>(requestHeaders);
            LOG.info("querying for Βεβαίωση Οικογενειακής Κατάστασης for" + firstname + "with surname " + lastname);
            LOG.info("the url is:: " + minEduQueryIdUrl);
            try {
                ResponseEntity<MinEduFamilyStatusResponse> familyStatusResponseEntity = restTemplate.exchange(minEduQueryIdUrl, HttpMethod.GET, entity, MinEduFamilyStatusResponse.class);
                MinEduFamilyStatusResponse familyStatusResp = familyStatusResponseEntity.getBody();
                return Optional.of(familyStatusResp);
            } catch (HttpClientErrorException e) {
                LOG.error(e.getMessage());
            }
        }
        LOG.error("no token found in response!");
        return Optional.empty();
    }

}
