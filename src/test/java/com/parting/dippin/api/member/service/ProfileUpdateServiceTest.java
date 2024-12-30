package com.parting.dippin.api.member.service;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

import com.parting.dippin.common.DatabaseTest;
import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.domain.member.service.ProfileUpdateService;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.MemberStatus;
import com.parting.dippin.entity.member.enums.SocialProvider;
import com.parting.dippin.entity.member.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@DatabaseTest
class ProfileUpdateServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProfileUpdateService profileUpdateService;

    @Transactional
    @DisplayName("프로필 수정 DTO를 받아 프로필을 수정한다.")
    @Test
    void updateProfile() {
        // given
        String newNickname = "new username";
        String newProfileUrl = "new profileImageUrl";
        String currentNickname = "current nickname";
        String currentProfileImageUrl = "current profileImageUrl";

        MemberEntity memberEntity = newMemberEntity(currentNickname, currentProfileImageUrl);
        MemberEntity save = memberRepository.save(memberEntity);

        // when
        profileUpdateService.updateProfile(save.getMemberId(), newProfileUrl, newNickname);

        // then
        MemberEntity retrievedMember = memberRepository.findById(save.getMemberId())
            .orElseThrow(() -> CommonException.from(INVALID_USER_ID));

        assertThat(retrievedMember.getNickname()).isEqualTo(newNickname);
        assertThat(retrievedMember.getProfileImageUrl()).isEqualTo(newProfileUrl);
    }

    @Transactional
    @DisplayName("프로필 수정 DTO에 nickname만 전달하면 닉네임만 수정된다.")
    @Test
    void updateProfileWithNickname() {
        // given
        String newNickname = "new username";
        String currentNickname = "current nickname";
        String currentProfileImageUrl = "current profileImageUrl";

        MemberEntity memberEntity = newMemberEntity(currentNickname, currentProfileImageUrl);
        MemberEntity save = memberRepository.save(memberEntity);

        // when
        profileUpdateService.updateProfile(save.getMemberId(), null, newNickname);

        // then
        MemberEntity retrievedMember = memberRepository.findById(save.getMemberId())
            .orElseThrow(() -> CommonException.from(INVALID_USER_ID));

        assertThat(retrievedMember.getNickname()).isEqualTo(newNickname);
        assertThat(retrievedMember.getProfileImageUrl()).isEqualTo(currentProfileImageUrl);
    }

    @Transactional
    @DisplayName("프로필 수정 DTO에 profileImageUrl만 전달하면 프로필 이미지만 수정된다.")
    @Test
    void updateProfileWithProfileImageUrl() {
        // given
        String newProfileImageUrl = "new profileImageUrl";
        String currentNickname = "current nickname";
        String currentProfileImageUrl = "current profileImageUrl";

        MemberEntity memberEntity = newMemberEntity(currentNickname, currentProfileImageUrl);
        MemberEntity save = memberRepository.save(memberEntity);

        // when
        profileUpdateService.updateProfile(save.getMemberId(), newProfileImageUrl, null);

        // then
        MemberEntity retrievedMember = memberRepository.findById(save.getMemberId())
            .orElseThrow(() -> CommonException.from(INVALID_USER_ID));

        assertThat(retrievedMember.getNickname()).isEqualTo(currentNickname);
        assertThat(retrievedMember.getProfileImageUrl()).isEqualTo(newProfileImageUrl);
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