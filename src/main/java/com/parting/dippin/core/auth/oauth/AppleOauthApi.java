package com.parting.dippin.core.auth.oauth;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.INTERNAL_SERVER_ERROR;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.core.exception.CommonException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleOauthApi implements OauthApi {

    public static final String ID_TOKEN = "id_token";
    public static final String EC_ALGORITHM = "EC";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String GRANT_TYPE = "grant_type";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String CODE = "code";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String CLIENT_ID = "client_id";
    public static final String AUDIENCE_URI = "https://appleid.apple.com";

    @Value("${apple.client.id}")
    private String appleClientId;
    @Value("${apple.uri.redirect}")
    private String appleRedirectUri;
    @Value("${apple.uri.token-request}")
    private String appleTokenRequestUri;
    @Value("${apple.team.id}")
    private String appleTeamId;
    @Value("${apple.key.id}")
    private String appleKeyId;
    @Value("${apple.key.content}")
    private String privateKeyContent;

    private Key applePrivateKey;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @PostConstruct
    public void initPrivateKey() {
        try {
            byte[] encoded = Base64.getDecoder().decode(privateKeyContent);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance(EC_ALGORITHM);
            applePrivateKey = kf.generatePrivate(keySpec);
        } catch (Exception e) {
            log.error("failed to init apple private key");
            log.error(e.getMessage());
        }
    }

    @Override
    public String getToken(String code) throws JsonProcessingException {
        String clientSecret = generateClientSecret();
        // 2. GET ID TOKEN
        HttpEntity<MultiValueMap<String, String>> requestEntity = generateTokenRequestEntity(code, clientSecret);

        /* post 메소드 요청하여 response 받기 */
        ResponseEntity<String> response = restTemplate.postForEntity(
                appleTokenRequestUri,
                requestEntity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get(ID_TOKEN).asText();
        }

        throw CommonException.from(INTERNAL_SERVER_ERROR);
    }

    private String generateClientSecret() {
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(60);

        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, appleKeyId)
                .setIssuer(appleTeamId)
                .setAudience(AUDIENCE_URI)
                .setSubject(appleClientId)
                .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .setIssuedAt(new Date())
                .signWith(applePrivateKey, SignatureAlgorithm.ES256)
                .compact();
    }

    @NotNull
    private HttpEntity<MultiValueMap<String, String>> generateTokenRequestEntity(String code, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(GRANT_TYPE, AUTHORIZATION_CODE);
        map.add(CLIENT_ID, appleClientId);
        map.add(CLIENT_SECRET, clientSecret);
        map.add(CODE, code);
        map.add(REDIRECT_URI, appleRedirectUri);

        return new HttpEntity<>(map, headers);
    }

    @Override
    public String getUser(String idToken) {
        DecodedJWT jwt = JWT.decode(idToken);

        return jwt.getSubject();
    }
}
