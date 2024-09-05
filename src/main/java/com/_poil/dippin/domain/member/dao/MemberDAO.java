package com._poil.dippin.domain.member.dao;

import com._poil.dippin.domain.member.dto.MemberDto;
import com._poil.dippin.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberDAO implements IMemberDAO {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberDto> find() {
        return this.memberRepository.findMemberList();
    }
}
