package com.parting.dippin.domain.auth.service;

import static com.parting.dippin.core.common.constant.JwtConstant.ACCESS_TOKEN_VALIDITY_30_MINUTES;
import static com.parting.dippin.core.common.constant.JwtConstant.REFRESH_TOKEN_VALIDITY_2_WEEKS;
import static com.parting.dippin.domain.auth.exception.AuthenticationCodeAndMessage.INVALID_TOKEN_TYPE;

import com.parting.dippin.core.common.auth.TokenProvider;
import com.parting.dippin.domain.auth.exception.AuthenticationTypeException;
import com.parting.dippin.entity.jwt.BlacklistRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofMillis(ACCESS_TOKEN_VALIDITY_30_MINUTES);
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofMillis(REFRESH_TOKEN_VALIDITY_2_WEEKS);
    private final TokenProvider tokenProvider;
    private final BlacklistRepository blacklistRepository;

    public void addAccessToken(String accessToken) {
        if (!tokenProvider.isAccessToken(accessToken)) {
            throw AuthenticationTypeException.from(INVALID_TOKEN_TYPE);
        }

        blacklistRepository.addToBlacklist(accessToken, ACCESS_TOKEN_DURATION);
    }

    public void addRefreshToken(String refreshToken) {
        if (!tokenProvider.isRefreshToken(refreshToken)) {
            throw AuthenticationTypeException.from(INVALID_TOKEN_TYPE);
        }

        blacklistRepository.addToBlacklist(refreshToken, REFRESH_TOKEN_DURATION);
    }
}
