package com.parting.dippin.core.auth.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleOauthApi implements OauthApi {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String SOCIAL_ID = "id";
    public static final String GRANT_TYPE = "grant_type";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String CLIENT_ID = "client_id";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String CODE = "code";
    public static final String CLIENT_SECRET = "client_secret";
    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.secret}")
    private String googleClientSecret;
    @Value("${google.uri.redirect}")
    private String googleRedirectUri;
    @Value("${google.uri.token-request}")
    private String googleTokenRequestUri;
    @Value("${google.uri.user-request}")
    private String googleUserInfoRequestUri;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public String getToken(String code) throws JsonProcessingException {
        /* 카카오 토큰 요청에 필요한 값 지정 */
        HttpEntity<Map<String, String>> requestEntity = generateTokenRequestEntity(code);
//
//        /* post 메소드 요청하여 response 받기 */
        ResponseEntity<String> response = restTemplate.postForEntity(
                googleTokenRequestUri,
                requestEntity,
                String.class
        );
//
//        /* response 제대로 받아오면 값 파싱 */
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get(ACCESS_TOKEN).asText();
        }

        // TODO 구글, 애플, 카카오 로그인 모두 완료후 에러 처리 예정
        throw new RuntimeException("외부 서버 통신 오류");
    }

    @NotNull
    private HttpEntity<Map<String, String>> generateTokenRequestEntity(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(CODE, code);
        requestBody.put(CLIENT_ID, googleClientId);
        requestBody.put(CLIENT_SECRET, googleClientSecret);
        requestBody.put(REDIRECT_URI, googleRedirectUri);
        requestBody.put(GRANT_TYPE, AUTHORIZATION_CODE);

        return new HttpEntity<>(requestBody, headers);
    }

//    @Override
    public String getUser(String token) throws JsonProcessingException {
        HttpEntity<Map<String, String>> requestEntity = generateUserInfoRequestEntity(token);

        /* get 메소드 요청하여 response 받기 */
        ResponseEntity<String> response = restTemplate.exchange(
                googleUserInfoRequestUri,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        /* response 제대로 받아오면 값 파싱 */
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            return jsonNode.get(SOCIAL_ID).asText();
        }

//        // TODO 예외 처리 PR 머지 후 수정예정
        throw new RuntimeException("외부 서버 통신 오류");
    }
//
    @NotNull
    private static HttpEntity<Map<String, String>> generateUserInfoRequestEntity(String token) {
        /* 카카오 유저정보 요청에 필요한 값 지정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        /* request 엔티티 만들어주기 */
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(null, headers);
        return requestEntity;
    }
}
