package com.parting.dippin.api.member.service;

import com.parting.dippin.domain.member.MemberRegister;
import com.parting.dippin.domain.member.dto.MemberRegisterDto;
import com.parting.dippin.domain.member.service.MemberCodeGeneratorService;
import com.parting.dippin.domain.member.service.MemberRegisterService;
import com.parting.dippin.domain.member.service.NicknameGeneratorService;
import com.parting.dippin.entity.member.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRegisterService memberRegisterService;
    private final NicknameGeneratorService nicknameGeneratorService;
    private final MemberCodeGeneratorService memberCodeGeneratorService;

    public MemberEntity signUp(MemberRegisterDto memberRegisterDto) {
        MemberRegister memberRegister = new MemberRegister();
        memberRegister.register(
                memberRegisterService,
                nicknameGeneratorService,
                memberCodeGeneratorService,
                memberRegisterDto
        );

        return MemberEntity.from(memberRegister);
    }
}
