package com.parting.dippin.domain.member;

import com.parting.dippin.domain.member.dto.MemberRegisterDto;
import com.parting.dippin.domain.member.service.MemberCodeGeneratorService;
import com.parting.dippin.domain.member.service.MemberRegisterService;
import com.parting.dippin.domain.member.service.NicknameGeneratorService;
import com.parting.dippin.entity.member.enums.SocialProvider;
import lombok.Getter;

@Getter
public class MemberRegister {

    private int memberId;
    private String nickname;
    private SocialProvider socialProvider;
    private String socialId;
    private String memberCode;

    public void register(
        MemberRegisterService memberRegisterService,
        NicknameGeneratorService nicknameGeneratorService,
        MemberCodeGeneratorService memberCodeGeneratorService,
        MemberRegisterDto memberRegisterDto
    ) {

        socialProvider = memberRegisterDto.getSocialProvider();
        socialId = memberRegisterDto.getSocialId();

        nickname = nicknameGeneratorService.generate();
        memberCode = memberCodeGeneratorService.generate();
        memberId = memberRegisterService.register(this);
    }
}
