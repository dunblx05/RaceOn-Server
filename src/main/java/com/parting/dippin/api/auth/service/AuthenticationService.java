package com.parting.dippin.api.auth.service;

import static com.parting.dippin.api.auth.exception.AuthenticationCodeAndMessage.USER_NOT_REGISTERED;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_USER_TOKEN;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.api.auth.dto.PostLoginReqDto;
import com.parting.dippin.api.auth.exception.AuthenticationTypeException;
import com.parting.dippin.core.auth.oauth.IdTokenParser;
import com.parting.dippin.core.common.CurrentTimeProvider;
import com.parting.dippin.core.common.auth.TokenProvider;
import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.SocialProvider;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenProvider tokenProvider;
    private final MemberReader memberReader;
    private final IdTokenParser idTokenParser;

    public GetJwtResDto login(int memberId) {
        return tokenProvider.createJwt(memberId);
    }

    public GetJwtResDto reissue(int memberId) {
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
}
