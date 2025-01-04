package com.parting.dippin.api.auth.service;

import com.parting.dippin.api.member.service.MemberService;
import com.parting.dippin.core.auth.oauth.AppleOauthApi;
import com.parting.dippin.core.common.auth.TokenProvider;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.member.enums.SocialProvider;
import org.springframework.stereotype.Service;

@Service
public class AppleOauthService extends OauthService{
    public AppleOauthService(
            AppleOauthApi appleOauthApi,
            MemberReader memberReader,
            TokenProvider tokenProvider,
            MemberService memberService
    ) {
        super(appleOauthApi, memberReader, tokenProvider, memberService);
    }

    @Override
    SocialProvider socialProvider() {
        return SocialProvider.APPLE;
    }
}
