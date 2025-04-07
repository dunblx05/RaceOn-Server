package com.parting.dippin.api.auth.service;

import static com.parting.dippin.domain.auth.exception.AuthenticationCodeAndMessage.USER_NOT_REGISTERED;

import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.api.auth.dto.LogoutReqDto;
import com.parting.dippin.api.auth.dto.PostLoginReqDto;
import com.parting.dippin.domain.auth.exception.AuthenticationTypeException;
import com.parting.dippin.core.auth.oauth.IdTokenParser;
import com.parting.dippin.core.common.auth.TokenProvider;
import com.parting.dippin.domain.auth.TokenBlacklistRegister;
import com.parting.dippin.domain.auth.service.TokenBlacklistService;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.domain.token.service.TokenDeleteService;
import com.parting.dippin.entity.member.MemberEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenBlacklistService tokenBlacklistService;
    private final TokenProvider tokenProvider;
    private final MemberReader memberReader;
    private final IdTokenParser idTokenParser;
    private final TokenDeleteService fcmTokenDeleteService;

    public GetJwtResDto login(int memberId) {
        return tokenProvider.createJwt(memberId);
    }

    public GetJwtResDto reissue(int memberId, HttpServletRequest request) {
        String refreshToken = tokenProvider.resolveToken(request);
        TokenBlacklistRegister tokenBlacklistRegister = new TokenBlacklistRegister();

        tokenBlacklistRegister.registerInBlacklist(
                tokenBlacklistService,
                tokenProvider,
                refreshToken,
                memberId
        );

        return tokenProvider.createJwt(memberId);
    }

    public GetJwtResDto login(PostLoginReqDto postLoginReqDto) {
        String socialId = idTokenParser.extractSocialId(
                postLoginReqDto.getIdToken(),
                postLoginReqDto.getSocialProvider()
        );

        MemberEntity member = memberReader.getMemberByOauthId(
                socialId,
                postLoginReqDto.getSocialProvider()
        ).orElseThrow(() -> AuthenticationTypeException.from(USER_NOT_REGISTERED));

        return tokenProvider.createJwt(member.getMemberId());
    }

    public void logout(int memberId, LogoutReqDto logoutReqDto) {
        TokenBlacklistRegister tokenBlacklistRegister = new TokenBlacklistRegister();

        tokenBlacklistRegister.registerInBlacklist(
                tokenBlacklistService,
                tokenProvider,
                logoutReqDto,
                memberId
        );

        if (logoutReqDto.getFcmToken() != null) {
            fcmTokenDeleteService.delete(logoutReqDto.getFcmToken());
        }
    }
}
