package com.parting.dippin.domain.member.service;

import com.parting.dippin.core.exception.UserNotFoundException;
import com.parting.dippin.domain.member.dto.MemberDto;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.SocialProvider;
import com.parting.dippin.entity.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
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

    public String getMemberCode(int memberId) {
        MemberEntity member = this.memberRepository.findMemberEntityByMemberId(memberId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않아요."));

        return member.getMemberCode();
    }

    public String getNickname(int memberId) {
        MemberEntity member = this.memberRepository.findMemberEntityByMemberId(memberId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않아요."));

        return member.getNickname();
    }

    public Optional<MemberEntity> getMemberByOauthId(String socialId, SocialProvider socialProvider) {
        return this.memberRepository.findMemberEntityBySocialIdAndSocialProvider(socialId, socialProvider);
    }
}
