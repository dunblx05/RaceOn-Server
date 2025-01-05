package com.parting.dippin.api.friend.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.parting.dippin.common.DatabaseTest;
import com.parting.dippin.entity.friends.repository.FriendsRepository;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.MemberStatus;
import com.parting.dippin.entity.member.enums.SocialProvider;
import com.parting.dippin.entity.member.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTest
class FriendServiceTest {

    @Autowired
    private FriendService friendService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FriendsRepository friendsRepository;

    @Test
    void addFriend() {
        // given
        String myMemberCode = "TTTTTT";
        String friendCode = "UUUUUU";
        MemberEntity memberEntity = newMemberEntity(myMemberCode);
        MemberEntity friend = newMemberEntity(friendCode);

        MemberEntity savedMember = memberRepository.save(memberEntity);
        MemberEntity savedFriend = memberRepository.save(friend);


        // when
        friendService.addFriend(savedMember.getMemberId(), savedFriend.getMemberCode());

        // then
        boolean isFriend = friendsRepository.existsFriendsByMyMemberIdAndMemberId(savedMember.getMemberId(),
                savedFriend.getMemberId());

        assertThat(isFriend).isTrue();
    }

    @Test
    void deleteFriend() {
        // given
        String myMemberCode = "FFFFFF";
        String friendCode = "ABDCEF";
        MemberEntity memberEntity = newMemberEntity(myMemberCode);
        MemberEntity friend = newMemberEntity(friendCode);

        MemberEntity savedMember = memberRepository.save(memberEntity);
        MemberEntity savedFriend = memberRepository.save(friend);

        friendService.addFriend(savedMember.getMemberId(), savedFriend.getMemberCode());

        // when
        friendService.deleteFriend(savedMember.getMemberId(), savedFriend.getMemberId());

        // then
        boolean isFriend = friendsRepository.existsFriendsByMyMemberIdAndMemberId(savedMember.getMemberId(),
                savedFriend.getMemberId());

        assertThat(isFriend).isFalse();
    }

    private static MemberEntity newMemberEntity(String memberCode) {
        return MemberEntity.builder()
                .nickname("nickname")
                .profileImageUrl("https://profile.image.com")
                .memberCode(memberCode)
                .socialProvider(SocialProvider.KAKAO)
                .socialId("KAKAO1")
                .memberStatus(MemberStatus.ACTIVE)
                .lastActiveAt(LocalDateTime.now())
                .build();
    }
}