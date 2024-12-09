package com.parting.dippin.domain.member.service;

import com.parting.dippin.domain.member.MemberRegister;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberRegisterService {

    private final MemberRepository memberRepository;

    public int register(MemberRegister memberRegister) {
        MemberEntity memberEntity = MemberEntity.from(memberRegister);

        memberEntity = memberRepository.save(memberEntity);

        return memberEntity.getMemberId();
    }
}
