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
import java.util.HashMap;
import java.util.Map;
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
    private Optional<Map<String, OAuths>> activeTokensMap;
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
        this.activeTokensMap = Optional.of(new HashMap());
    }

    @Override
    public Optional<String> getAccessTokenByTokenTypeEn(String tokenType) throws HttpClientErrorException {
        GrantRequest grantReq = new GrantRequest(minEduTokenUser, minEduTokenPass, minEduTokenGrantType);
        RestTemplate restTemplate = new RestTemplate();
        LOG.info("will get toke from theurl: " + minEduTokenUri + " for token Type " + tokenType);
        try {
            if (activeTokensMap.isPresent() && activeTokensMap.get().get(tokenType) != null && accessTokenExpiration.isAfter(LocalDateTime.now().plusSeconds(30))) {
                LOG.info("MinEdu OAth token still alive " + activeTokensMap.get().get(tokenType));
                return Optional.of(activeTokensMap.get().get(tokenType).getOauth().getAccessToken());
            } else {
//                LOG.info("will get new token ");
                TokenResponse tokResp = restTemplate.postForObject(minEduTokenUri, grantReq, TokenResponse.class);
                if (tokResp != null && tokResp.getSuccess().equals("true") && tokResp.getOauths() != null) {
                    Arrays.stream(tokResp.getOauths()).forEach(token -> {
                        activeTokensMap.get().put(token.getTokenTypeEN(), token);
                    });

                    Optional<OAuths> oAuth = Arrays.stream(tokResp.getOauths()).filter(token -> {
                        return token.getTokenTypeEN().equals(tokenType);
                    }).findFirst();

                    if (oAuth.isPresent()) {
                        this.accessTokenExpiration = this.accessTokenExpiration.plusSeconds(oAuth.get().getOauth().getExpiresIn());
                        return Optional.of(this.activeTokensMap.get().get(tokenType).getOauth().getAccessToken());
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
        try {
            if (activeTokensMap.isPresent() && activeTokensMap.get().get(type) != null && accessTokenExpiration.isAfter(LocalDateTime.now().plusSeconds(30))) {
                LOG.info("MinEdu OAth token still alive for " + type + " " + activeTokensMap.get().get(type).getOauth().getAccessToken());
                return Optional.of(activeTokensMap.get().get(type).getOauth().getAccessToken());
            } else {
                LOG.info("will get token from theurl: " + minEduTokenUri + " for token Type " + type);
                TokenResponse tokResp = restTemplate.postForObject(minEduTokenUri, grantReq, TokenResponse.class);
                if (tokResp != null && tokResp.getSuccess().equals("true") && tokResp.getOauths() != null) {
                    Arrays.stream(tokResp.getOauths()).forEach(token -> {
                        LOG.info("adding token " + token.getTokenType() + " the token " + token.getOauth().getAccessToken());
                        activeTokensMap.get().put(token.getTokenType(), token);
                    });
                    Optional<OAuths> oAuth = Arrays.stream(tokResp.getOauths()).filter(token -> {
                        return token.getTokenType().equals(type);
                    }).findFirst();

                    if (oAuth.isPresent()) {
                        LOG.info("retrieved token ok " + oAuth.get().getOauth().getAccessToken());
                        this.accessTokenExpiration = this.accessTokenExpiration.plusSeconds(oAuth.get().getOauth().getExpiresIn());
                        return Optional.of(this.activeTokensMap.get().get(type).getOauth().getAccessToken());
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            LOG.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<AmkaResponse> getAmkaFullResponse(String amka, String surname) {
        HttpHeaders requestHeaders = new HttpHeaders();
        Optional<String> accessToken = getAccessToken("Δεδομένα ΑΜΚΑ API");
        if (accessToken.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            requestHeaders.add("Authorization", "Bearer " + accessToken.get());
            HttpEntity<?> entity = new HttpEntity<>(requestHeaders);
            LOG.info("querying for amka " + amka + " with surname " + surname);
            try {
                String supervisorusername = System.getenv("SUPERVISOR_NAME");
                String supervisorpassword = System.getenv("SUPERVISOR_PASSWORD");
                String minEduQueryIdUrl = this.minEduAmkaQueryEndpoint + "/" + amka + "/" + surname
                        + "/extended?gdpruser=" + supervisorusername + "&gdprpass=" + supervisorpassword;

                ResponseEntity<MinEduAmkaResponse> amkaResponseEntity = restTemplate.exchange(minEduQueryIdUrl, HttpMethod.GET, entity, MinEduAmkaResponse.class
                );
                MinEduAmkaResponse amkaResp = amkaResponseEntity.getBody();
                LOG.info(amkaResp.toString());
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
                ResponseEntity<MinEduCheckByAmkaResponse> queryResponse = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, MinEduCheckByAmkaResponse.class
                );
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
                ResponseEntity<MinEduResponse> queryId = restTemplate.exchange(minEduQueryIdUrl, HttpMethod.GET, entity, MinEduResponse.class
                );
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
                ResponseEntity<MinEduFamilyStatusResponse> familyStatusResponseEntity = restTemplate.exchange(minEduQueryIdUrl, HttpMethod.GET, entity, MinEduFamilyStatusResponse.class
                );
                MinEduFamilyStatusResponse familyStatusResp = familyStatusResponseEntity.getBody();
                return Optional.of(familyStatusResp);
            } catch (HttpClientErrorException e) {
                LOG.error(e.getMessage());
            }
        }
        LOG.error("no token found in response!");
        return Optional.empty();
    }

    @Override
    public Optional<MinEduFamilyStatusResponse> getBirthCertificateResponse(String firstname, String lastname, String fathername, String mothername, String birthdate, String supervisorusername, String supervisorpassword) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(this.minEduMitroPolitonEndpoint)
                .append(firstname).append("/")
                .append(lastname).append("/")
                .append(fathername).append("/")
                .append(mothername).append("/")
                .append(birthdate).append("?")
                .append("supervisorusername=").append(supervisorusername).append("&")
                .append("supervisorpassword=").append(supervisorpassword).append("&")
                .append("servicename=").append("Βεβαίωση Γέννησης με ΑΜΚΑ");
        String minEduQueryIdUrl = urlBuilder.toString();
        HttpHeaders requestHeaders = new HttpHeaders();
        Optional<String> accessToken = getAccessToken("Δεδομένα Μητρώου Πολιτών");
        if (accessToken.isPresent()) {
            RestTemplate restTemplate = new RestTemplate();
            requestHeaders.add("Authorization", "Bearer " + accessToken.get());
            HttpEntity<?> entity = new HttpEntity<>(requestHeaders);
            LOG.info("querying for Βεβαίωση Βεβαίωση Γέννησης με ΑΜΚΑ for" + firstname + "with surname " + lastname);
            LOG.info("the url is:: " + minEduQueryIdUrl);
            try {
                ResponseEntity<MinEduFamilyStatusResponse> familyStatusResponseEntity = restTemplate.exchange(minEduQueryIdUrl, HttpMethod.GET, entity, MinEduFamilyStatusResponse.class
                );
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
