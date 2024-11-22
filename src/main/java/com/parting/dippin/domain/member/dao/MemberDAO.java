package com.parting.dippin.domain.member.dao;

import com.parting.dippin.domain.member.dto.MemberDto;
import com.parting.dippin.entity.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberDAO implements IMemberDAO {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberDto> getMembers(String nickname, int myMemberId) {
        return this.memberRepository.findMemberAndIsFriendByNicknameAndMemberId(nickname, myMemberId);
    }
}
