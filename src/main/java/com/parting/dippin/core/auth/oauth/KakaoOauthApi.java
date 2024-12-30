package com.parting.dippin.core.auth.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoOauthApi implements OauthApi {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String SOCIAL_ID = "id";
    @Value("${kakao.client.id}")
    private String kakaoClientId;
    @Value("${kakao.uri.redirect}")
    private String kakaoRedirectUri;
    @Value("${kakao.uri.token-request}")
    private String kakaoTokenRequestUri;
    @Value("${kakao.uri.user-request}")
    private String kakaoUserInfoRequestUri;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public String getToken(String code) throws JsonProcessingException {
        /* 카카오 토큰 요청에 필요한 값 지정 */
        HttpEntity<MultiValueMap<String, String>> requestEntity = generateTokenRequestEntity(code);

        /* post 메소드 요청하여 response 받기 */
        ResponseEntity<String> response = restTemplate.postForEntity(
                kakaoTokenRequestUri,
                requestEntity,
                String.class
        );

        /* response 제대로 받아오면 값 파싱 */
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get(ACCESS_TOKEN).asText();
        }

        // TODO 예외 처리 PR 머지 후 수정예정
        throw new RuntimeException("외부 서버 통신 오류");
    }

    @NotNull
    private HttpEntity<MultiValueMap<String, String>> generateTokenRequestEntity(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.set("grant_type", "authorization_code");
        requestBody.set("client_id", kakaoClientId);
        requestBody.set("redirect_uri", kakaoRedirectUri);
        requestBody.set("code", code);

        return new HttpEntity<>(requestBody, headers);
    }

    @Override
    public String getUser(String token) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> requestEntity = genereateUserInfoRequestEntity(
                token);

        /* get 메소드 요청하여 response 받기 */
        ResponseEntity<String> response = restTemplate.exchange(
                kakaoUserInfoRequestUri,
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

        // TODO 예외 처리 PR 머지 후 수정예정
        throw new RuntimeException("외부 서버 통신 오류");
    }

    @NotNull
    private static HttpEntity<MultiValueMap<String, String>> genereateUserInfoRequestEntity(String token) {
        /* 카카오 유저정보 요청에 필요한 값 지정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        /* request 엔티티 만들어주기 */
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
        return requestEntity;
    }
}
