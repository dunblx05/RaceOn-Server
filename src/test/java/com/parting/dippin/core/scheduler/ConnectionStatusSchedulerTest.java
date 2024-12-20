package com.parting.dippin.core.scheduler;

import static org.assertj.core.api.Assertions.*;

import com.parting.dippin.common.DatabaseTest;
import com.parting.dippin.entity.connection.ConnectionStatusEntity;
import com.parting.dippin.entity.connection.repository.ConnectionRepository;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.MemberStatus;
import com.parting.dippin.entity.member.enums.SocialProvider;
import com.parting.dippin.entity.member.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

@DatabaseTest
class ConnectionStatusSchedulerTest {

    @Autowired
    private ConnectionStatusScheduler scheduler;
    private RedisTemplate<Integer, ConnectionStatusEntity> connectionRedisTemplate;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Test
    void writeBackVotes() {
        // given
        LocalDateTime lastActiveAt = LocalDateTime.of(2024, 11, 1, 12, 0);
        LocalDateTime now = LocalDateTime.now();

        MemberEntity member1 = memberRepository.save(newMemberEntity(lastActiveAt,"AAAAAA"));
        ConnectionStatusEntity connectionStatus1 = generateConnectionStatus(member1.getMemberId(), now);
        connectionRepository.save(connectionStatus1);

        // when
        scheduler.writeBackVotes();

        //then
        MemberEntity memberEntity1 = memberRepository.findById(member1.getMemberId())
                .orElseThrow();

        assertThat(memberEntity1.getLastActiveAt()).isEqualTo(now);
    }

    private ConnectionStatusEntity generateConnectionStatus(int memberId, LocalDateTime now) {
        return ConnectionStatusEntity.builder()
                .memberId(memberId)
                .lastActiveAt(now)
                .isPlaying(false)
                .build();
    }

    private static MemberEntity newMemberEntity(LocalDateTime lastActiveAt, String memberCode) {
        return MemberEntity.builder()
                .nickname("nickname")
                .profileImageUrl("profileImage")
                .memberCode(memberCode)
                .socialProvider(SocialProvider.KAKAO)
                .socialId("KAKAO1")
                .memberStatus(MemberStatus.ACTIVE)
                .lastActiveAt(lastActiveAt)
                .build();
    }
}