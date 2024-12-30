package com.parting.dippin.api.auth.service;

import com.parting.dippin.api.member.service.MemberService;
import com.parting.dippin.core.common.auth.TokenProvider;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.member.enums.SocialProvider;
import com.parting.dippin.core.auth.oauth.KakaoOauthApi;
import org.springframework.stereotype.Service;

@Service
public class KakaoOauthService extends OauthService {

    public KakaoOauthService(
            KakaoOauthApi kakaoOauthApi,
            MemberReader memberReader,
            TokenProvider tokenProvider,
            MemberService memberService
    ) {
        super(kakaoOauthApi, memberReader, tokenProvider, memberService);
    }

    @Override
    SocialProvider socialProvider() {
        return SocialProvider.KAKAO;
    }
}
