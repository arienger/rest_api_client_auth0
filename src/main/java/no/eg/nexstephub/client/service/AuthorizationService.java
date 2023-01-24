package no.eg.nexstephub.client.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import no.eg.nexstephub.client.domain.BearerAuthenticationToken;
import no.eg.nexstephub.client.domain.Client;
import no.eg.nexstephub.client.model.GeneralResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
public class AuthorizationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    private Client client;

    public AuthorizationService() {
    }

//    todo: make this work!
//    private BearerAuthenticationToken getAuthorizationTokenGoogle(Client client) {
//        try {
//            // todo finish!!!
//            final HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            String body = "response_type=code&client_id=" + client.getClientId() + "&scope=[email profile openid]";
//            final HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);
//            String urlForAuthorizationCode = client.getUrlForAuthorizationCode() + "?response_type=code&client_id=" + client.getClientId() + "&scope=[email profile openid]";
//
//
//            ResponseEntity<String> response = restTemplate.getForEntity(urlForAuthorizationCode, String.class);
//            //
//
//            logger.error("hei2");

            //ResponseEntity<JsonNode> response = restTemplate.exchange(client.getUrlForAuthorizationCode(), HttpMethod.POST, request, JsonNode.class);

//            final HttpHeaders httpHeaders = new HttpHeaders();
//            String auth = client.getClientId() + ":" + client.getClientSecret();
//            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
//            String authHeader = "Basic " + new String(encodedAuth);
//            httpHeaders.add(HttpHeaders.AUTHORIZATION, authHeader);
//            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            String body = "grant_type=" + client.getGrantType().trim() + "&code=xxx";
//            final HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);
//
//            ResponseEntity<JsonNode> response = restTemplate.exchange(client.getUrlForGetToken(), HttpMethod.POST, request, JsonNode.class);
//            return null;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return null;
//        }
//    }

    public BearerAuthenticationToken getBearerAuthenticationToken() {
        if (client.getClientType() == Client.ClientTypeEnum.AUTH0) {
            return getAuth0ApiToken();
        }
        else {
            String s = "Unsupported client type " + client.getClientType();
            logger.error(s);
            throw new IllegalArgumentException(s);
        }
    }

    private BearerAuthenticationToken getAuth0ApiToken() {
        try {
            StringBuilder jsonBody = new StringBuilder("{\"client_id\":\"")
                    .append(client.getClientId())
                    .append("\",\"client_secret\":\"")
                    .append(client.getClientSecret())
                    .append("\",\"audience\":\"")
                    .append(client.getAudience())
                    .append("\",\"grant_type\":\"")
                    .append(client.getGrantType())
                    .append("\"}");

            HttpResponse<JsonNode> response = Unirest.post(client.getUrlForGetToken())
                    .header("content-type", "application/json")
                    .body(jsonBody.toString())
                    .asJson();

            if (response.isSuccess()) {
                return createAuth0BearerAuthenticationToken(response.getBody());
            } else {
                String s = "Failed to get AUTH0 api-token. status " + response.getStatus() + " " + response.getStatusText();
                logger.error(s);
                throw new HttpClientErrorException(HttpStatusCode.valueOf(response.getStatus()));
            }
        } catch (UnirestException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private BearerAuthenticationToken createAuth0BearerAuthenticationToken(JsonNode jsonNode) {
        kong.unirest.json.JSONObject jsonObject = jsonNode.getObject();
        Set<String> tokenKeySet = jsonObject.keySet();
        if (tokenKeySet == null || tokenKeySet.isEmpty())
            return null;
        String access_token = null;
        if (tokenKeySet.contains("access_token")) {
            access_token = (String) jsonObject.get("access_token");
        }
        String scope = null;
        if (tokenKeySet.contains("scope")) {
            scope = (String) jsonObject.get("scope");
        }
        String tokenType = null;
        if (tokenKeySet.contains("token_type")) {
            tokenType = (String) jsonObject.get("token_type");
        }
        Integer expiresIn = null;
        if (tokenKeySet.contains("expires_in")) {
            expiresIn = (Integer) jsonObject.get("expires_in");
        }

        BearerAuthenticationToken bearerAuthenticationToken = new BearerAuthenticationToken(client);
        bearerAuthenticationToken.setAccessToken(access_token);
        bearerAuthenticationToken.setScope(scope);
        bearerAuthenticationToken.setTokenType(tokenType);
        bearerAuthenticationToken.setExpiresInSeconds(expiresIn == null ? 0 : expiresIn);

        return bearerAuthenticationToken;
    }


//    public void request() {
//        try {
//            BearerAuthenticationToken bearerAuthenticationToken = null;
//            if (client.getClientType() == Client.ClientTypeEnum.AUTH0) {
//                bearerAuthenticationToken = getAuth0ApiToken();
//            } else if (client.getClientType() == Client.ClientTypeEnum.GOOGLE) {
//                // bearerAuthenticationToken = getAuthorizationTokenGoogle(client);
//                String s = "Unsupprted client type " + client.getClientType();
//                logger.error(s);
//                throw new IllegalArgumentException(s);
//            }
//            if (bearerAuthenticationToken != null && !bearerAuthenticationToken.isExpired()) {
//                logger.info("TOKEN is valid, expires " + bearerAuthenticationToken.getExpireDateTimeAsString());
//            } else {
//                logger.info("did not get any token (is null)");
//                return;
//            }
//            String url = "http://localhost:8080/api/private";
//            // Response response = callUsingOkHttpClient(url,bearerAuthenticationToken);
//            ResponseEntity<GeneralResponseDto> response = doGetRequest(url,bearerAuthenticationToken);
//            logger.info("finish");
//        } catch (Throwable t) {
//            logger.error(t.getMessage(), t);
//        }
//    }

    // todo make this work?
//    private Response callUsingOkHttpClient(String url, BearerAuthenticationToken bearerAuthenticationToken) {
//        try {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        //RequestBody body = RequestBody.create(mediaType, "");
//            RequestBody body = null;
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/api/private")
//                .method("GET", body)
//                .addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InZUeC1sNWxTeGJPVWNFaXB5aF9DZyJ9.eyJpc3MiOiJodHRwczovL2Rldi0yNjh1NGxzaS5ldS5hdXRoMC5jb20vIiwic3ViIjoiMGo0MkNoR3FrVVY2SUpVbldqU2QxM1ltajk5R3ZVRFBAY2xpZW50cyIsImF1ZCI6Imh0dHBzOi8vZGV2LTI2OHU0bHNpLmV1LmF1dGgwLmNvbS9hcGkvdjIvIiwiaWF0IjoxNjc0NDc2ODAwLCJleHAiOjE2NzQ1NjMyMDAsImF6cCI6IjBqNDJDaEdxa1VWNklKVW5XalNkMTNZbWo5OUd2VURQIiwic2NvcGUiOiJyZWFkOnVzZXJzIGNyZWF0ZTp1c2VycyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.GgQJNVYwZSAAHvpEFCRSqRXyMxvZuzDRvowFbGz8lOqgKWAlP6W7MYad9Fv2ni_Geev12A4kqrje_s0l_6bhBckdAm3MZ4aesP_OZVd3C5quht3rLXvIhi2LGF3wvmw_d96eiK2kUm2ASFZD5UQY8T2cTHP5hEJhRgaQX8fmNUn6UVXOsA1kq9Ld1-WW5PvOvigA1ehTMLPXbW11dBhTNY1IIMKkU_uhXo3omuHvu72Onq9aFvIoJ0GnHXolnxfiw4x7SlDPwbCK9SQg-4Fa3Bu8QbjkTn2t3Qrt3FOG2RUUGXp6KLjOKI7-_ky-djTcdnLSEmBMU2Q7k470l4ValQ")
//                .addHeader("Cookie", "JSESSIONID=7E15D64A124872F2EEBD0E7130E045FD")
//                .build();
//        Response response = client.newCall(request).execute();
//        return response;
//        } catch (Throwable t) {
//            logger.info(t.getMessage(), t);
//        }
//        return null;
//    }

//    private ResponseEntity<GeneralResponseDto> doGetRequest(String url, BearerAuthenticationToken bearerAuthenticationToken) {
//        final HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
//        String authToken = bearerAuthenticationToken.getAuthorizationToken();
//        httpHeaders.add(HttpHeaders.AUTHORIZATION, authToken);
//
//        HttpEntity request = new HttpEntity<>("", httpHeaders);
//        ResponseEntity response = null;
//        try {
//            response = restTemplate.exchange(url, HttpMethod.GET, request, com.fasterxml.jackson.databind.JsonNode.class);
//
//            if (!response.getStatusCode().is2xxSuccessful()) {
//                logger.error("Failed to call url " + url + ", status " + response.getStatusCode());
//                response = createResponse(response.getStatusCodeValue(), response.toString(), response.getStatusCode());
//            }
//            return response;
//        } catch (HttpClientErrorException e) {
//            int statusCode = e.getRawStatusCode();
//            logger.error("Failed, statuscode " + statusCode, e);
//            throw e;
//        } catch (Exception e) {
//            logger.error("Failed", e);
//            throw e;
//        }
//    }

    private static ResponseEntity<GeneralResponseDto> createResponse(int statusCode, String message, HttpStatusCode httpStatusCode) {
        GeneralResponseDto generalResponseDto = new GeneralResponseDto();
        generalResponseDto.setHttpStatusCode(statusCode);
        return new ResponseEntity<>(generalResponseDto, httpStatusCode);
    }
}
