package com.parting.dippin.api.member.service;

import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.core.auth.oauth.IdTokenParser;
import com.parting.dippin.core.common.auth.TokenProvider;
import com.parting.dippin.domain.member.MemberRegister;
import com.parting.dippin.domain.member.dto.MemberRegisterDto;
import com.parting.dippin.domain.member.service.MemberCodeGeneratorService;
import com.parting.dippin.domain.member.service.MemberRegisterService;
import com.parting.dippin.domain.member.service.MemberRegistrationValidator;
import com.parting.dippin.domain.member.service.NicknameGeneratorService;
import com.parting.dippin.entity.member.enums.SocialProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final IdTokenParser idTokenParser;
    private final TokenProvider tokenProvider;
    private final MemberRegistrationValidator memberRegistrationValidator;
    private final MemberRegisterService memberRegisterService;
    private final NicknameGeneratorService nicknameGeneratorService;
    private final MemberCodeGeneratorService memberCodeGeneratorService;

    public GetJwtResDto signUp(SocialProvider socialProvider, String socialId) {
        MemberRegister memberRegister = new MemberRegister();
        memberRegister.register(
                memberRegisterService,
                nicknameGeneratorService,
                memberCodeGeneratorService,
                memberRegistrationValidator,
                socialProvider,
                socialId
        );

        return tokenProvider.createJwt(memberRegister.getMemberId());
    }

    public GetJwtResDto signUp(MemberRegisterDto memberRegisterDto) {
        MemberRegister memberRegister = new MemberRegister();
        memberRegister.register(
                memberRegisterService,
                nicknameGeneratorService,
                memberCodeGeneratorService,
                memberRegistrationValidator,
                idTokenParser,
                memberRegisterDto
        );

        return tokenProvider.createJwt(memberRegister.getMemberId());
    }
}
