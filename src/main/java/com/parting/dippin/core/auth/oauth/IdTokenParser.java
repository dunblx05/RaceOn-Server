package com.parting.dippin.core.auth.oauth;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_USER_TOKEN;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.parting.dippin.core.common.CurrentTimeProvider;
import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.entity.member.enums.SocialProvider;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdTokenParser {
    public static final String ASIA_SEOUL = "Asia/Seoul";

    private final CurrentTimeProvider currentTimeProvider;

    public String extractSocialId(String idToken, SocialProvider socialProvider) {
        try {
            DecodedJWT jwt = JWT.decode(idToken);

            validateExpiresAt(jwt);
            validateIssuer(jwt, socialProvider);

            return jwt.getSubject();
        } catch (JWTDecodeException jwtDecodeException) {
            throw CommonException.from(INVALID_USER_TOKEN);
        }
    }

    private void validateExpiresAt(DecodedJWT jwt) {
        LocalDateTime expiresAt = extractExpiresAt(jwt);

        if (expiresAt.isBefore(currentTimeProvider.now())) {
            throw CommonException.from(INVALID_USER_TOKEN);
        }
    }

    private LocalDateTime extractExpiresAt(DecodedJWT jwt) {
        Date expiresAt = jwt.getExpiresAt();

        return LocalDateTime.ofInstant(expiresAt.toInstant(), ZoneId.of(ASIA_SEOUL));
    }

    private void validateIssuer(DecodedJWT jwt, SocialProvider socialProvider) {
        String issuer = jwt.getIssuer();

        if (!socialProvider.getIssuer().equals(issuer)) {
            throw CommonException.from(INVALID_USER_TOKEN);
        }
    }
}
