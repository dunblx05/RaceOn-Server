package com.parting.dippin.api.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.api.member.service.MemberService;
import com.parting.dippin.core.common.auth.TokenProvider;
import com.parting.dippin.domain.member.dto.MemberRegisterDto;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.SocialProvider;
import com.parting.dippin.core.auth.oauth.OauthApi;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public abstract class OauthService {

    final OauthApi oauthApi;
    final MemberReader memberReader;
    final TokenProvider tokenProvider;
    final MemberService memberService;

    OauthService(
            OauthApi oauthApi,
            MemberReader memberReader,
            TokenProvider tokenProvider,
            MemberService memberService
    ) {
        this.oauthApi = oauthApi;
        this.memberReader = memberReader;
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
    }

    abstract SocialProvider socialProvider();

    public GetJwtResDto callBack(String callbackToken) {
        try{
            String oauthApiToken = oauthApi.getToken(callbackToken);

            String socialId = oauthApi.getUser(oauthApiToken);
            MemberEntity member = memberReader.getMemberByOauthId(socialId, socialProvider())
                    .orElseGet(signUp(socialProvider(), socialId));

            return tokenProvider.createJwt(member.getMemberId());
        }catch (JsonProcessingException exception){
            // TODO 예외 처리 PR 머지 후 수정 예정.
            throw new RuntimeException("서버 외부 통신 에러");
        }
    }

    @NotNull
    Supplier<MemberEntity> signUp(SocialProvider socialProvider, String socialId) {
        return () -> {
            MemberRegisterDto registerDto = new MemberRegisterDto(socialProvider, socialId);

            return memberService.signUp(registerDto);
        };
    }
}
