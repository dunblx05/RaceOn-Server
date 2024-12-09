package com.parting.dippin.api.member.service;

import com.parting.dippin.domain.member.dto.MemberDto;
import com.parting.dippin.entity.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberReader {

    private final MemberRepository memberRepository;

    public List<MemberDto> getMembers(String nickname, int myMemberId) {
        return this.memberRepository.findMemberAndIsFriendByNicknameAndMemberId(nickname,
            myMemberId);
    }
}
