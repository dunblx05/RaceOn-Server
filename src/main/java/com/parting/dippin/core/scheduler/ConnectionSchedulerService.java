package com.parting.dippin.core.scheduler;

import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.connection.ConnectionStatusEntity;
import com.parting.dippin.entity.connection.repository.ConnectionRepository;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.repository.MemberRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConnectionSchedulerService {

    private final ConnectionRepository connectionRepository;
    private final MemberRepository memberRepository;
    private final MemberReader memberReader;

    @Transactional
    public void syncConnectionStatus(int memberId) {
        MemberEntity memberEntity = memberReader.getMemberById(memberId);

        LocalDateTime lastActiveAtFromDatabase = memberEntity.getLastActiveAt();
        LocalDateTime lastActiveAtFromRedis = getLastActiveAtFromRedis(memberId);

        if (lastActiveAtFromDatabase.isBefore(lastActiveAtFromRedis)) {
            memberEntity.updateLastActiveAt(lastActiveAtFromRedis);
            memberRepository.save(memberEntity);

            connectionRepository.deleteById(memberId);
        }
    }

    private LocalDateTime getLastActiveAtFromRedis(int memberId) {
        ConnectionStatusEntity redisMemberEntity = connectionRepository.findByMemberId(memberId);

        return redisMemberEntity.getLastActiveAt();
    }
}
