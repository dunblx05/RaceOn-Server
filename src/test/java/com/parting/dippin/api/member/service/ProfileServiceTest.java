package com.parting.dippin.api.member.service;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

import com.parting.dippin.common.DatabaseTest;
import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.MemberStatus;
import com.parting.dippin.entity.member.enums.SocialProvider;
import com.parting.dippin.entity.member.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTest
class ProfileServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileService profileService;

    @Test
    void getProfile() {
        String nickname = "username";
        String profileUrl = "profileImageUrl";

        MemberEntity memberEntity = newMemberEntity(nickname, profileUrl);
        MemberEntity save = memberRepository.save(memberEntity);

        // when
        profileService.getProfile(save.getMemberId());

        // then
        MemberEntity retrievedMember = memberRepository.findById(save.getMemberId())
                .orElseThrow(() -> CommonException.from(INVALID_USER_ID));

        assertThat(retrievedMember.getMemberId()).isEqualTo(save.getMemberId());
        assertThat(retrievedMember.getNickname()).isEqualTo(nickname);
        assertThat(retrievedMember.getProfileImageUrl()).isEqualTo(profileUrl);
    }

    private static MemberEntity newMemberEntity(String nickname, String profileImage) {
        return MemberEntity.builder()
                .nickname(nickname)
                .profileImageUrl(profileImage)
                .memberCode("AAAAAA")
                .socialProvider(SocialProvider.KAKAO)
                .socialId("KAKAO1")
                .memberStatus(MemberStatus.ACTIVE)
                .lastActiveAt(LocalDateTime.now())
                .build();
    }
}