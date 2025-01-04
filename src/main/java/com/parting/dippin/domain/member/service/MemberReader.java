package com.parting.dippin.domain.member.service;

import com.parting.dippin.core.exception.CommonCodeAndMessage;
import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.domain.member.dto.MemberDto;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.SocialProvider;
import com.parting.dippin.entity.member.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
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

    public MemberEntity getMemberById(int memberId) {
        return getMemberById(memberId, () -> CommonException.from(CommonCodeAndMessage.INVALID_USER_ID));
    }

    public MemberEntity getMemberById(int memberId, Supplier<RuntimeException> customException) {
        return this.memberRepository.findMemberEntityByMemberId(memberId)
                .orElseThrow(customException);
    }

    public String getMemberCode(int memberId) {
        MemberEntity member = getMemberById(memberId);

        return member.getMemberCode();
    }

    public String getNickname(int memberId) {
        MemberEntity member = getMemberById(memberId);

        return member.getNickname();
    }

    public Optional<MemberEntity> getMemberByOauthId(String socialId, SocialProvider socialProvider) {
        return this.memberRepository.findMemberEntityBySocialIdAndSocialProvider(socialId, socialProvider);
    }
}
