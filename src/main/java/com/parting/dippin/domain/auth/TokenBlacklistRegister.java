package com.parting.dippin.domain.auth;

import static com.parting.dippin.domain.auth.exception.AuthenticationCodeAndMessage.INVALID_TOKEN_OWNER;
import static com.parting.dippin.domain.auth.exception.AuthenticationCodeAndMessage.INVALID_TOKEN_TYPE;

import com.parting.dippin.api.auth.dto.LogoutReqDto;
import com.parting.dippin.core.common.auth.TokenProvider;
import com.parting.dippin.domain.auth.exception.AuthenticationTypeException;
import com.parting.dippin.domain.auth.service.TokenBlacklistService;

public class TokenBlacklistRegister {

    public void registerInBlacklist(
            TokenBlacklistService tokenBlacklistService,
            TokenProvider tokenProvider,
            String refreshToken,
            int myMemberId
    ) {
        validateTokenOwnership(tokenProvider, refreshToken, myMemberId);
        validateIsRefreshToken(tokenProvider, refreshToken);

        tokenBlacklistService.addRefreshToken(refreshToken);
    }


    public void registerInBlacklist(
            TokenBlacklistService tokenBlacklistService,
            TokenProvider tokenProvider,
            LogoutReqDto logoutReqDto,
            int myMemberId
    ) {
        String accessToken = logoutReqDto.getAccessToken();
        String refreshToken = logoutReqDto.getRefreshToken();

        validateTokenOwnership(tokenProvider, accessToken, myMemberId);
        validateTokenOwnership(tokenProvider, refreshToken, myMemberId);
        validateIsAccessToken(tokenProvider, accessToken);
        validateIsRefreshToken(tokenProvider, refreshToken);

        tokenBlacklistService.addAccessToken(accessToken);
        tokenBlacklistService.addRefreshToken(refreshToken);
    }

    private void validateTokenOwnership(
            TokenProvider tokenProvider,
            String token,
            int myMemberId
    ) {
        int memberIdFromToken = tokenProvider.extractMemberId(token);

        if (myMemberId != memberIdFromToken) {
            throw AuthenticationTypeException.from(INVALID_TOKEN_OWNER);
        }
    }

    private void validateIsAccessToken(TokenProvider tokenProvider, String accessToken) {
        if (!tokenProvider.isAccessToken(accessToken)) {
            throw AuthenticationTypeException.from(INVALID_TOKEN_TYPE);
        }
    }

    private void validateIsRefreshToken(TokenProvider tokenProvider, String refreshToken) {
        if (!tokenProvider.isRefreshToken(refreshToken)) {
            throw AuthenticationTypeException.from(INVALID_TOKEN_TYPE);
        }
    }
}
