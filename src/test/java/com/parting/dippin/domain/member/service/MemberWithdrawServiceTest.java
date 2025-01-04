package com.parting.dippin.domain.member.service;

import static com.parting.dippin.entity.member.enums.MemberStatus.DELETED;
import static org.assertj.core.api.Assertions.assertThat;

import com.parting.dippin.common.DatabaseTest;
import com.parting.dippin.common.TestCurrentTimeProvider;
import com.parting.dippin.entity.member.MemberEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTest
class MemberWithdrawServiceTest {

    @Autowired
    private MemberReader memberReader;

    @Autowired
    private MemberWithdrawService memberWithdrawService;

    @Autowired
    private TestCurrentTimeProvider testCurrentTimeProvider;

    @Test
    void withdraw() {
        // given
        int memberId = 1;

        testCurrentTimeProvider.setCurrentTime(LocalDateTime.now());
        LocalDateTime now = testCurrentTimeProvider.now();

        // when
        memberWithdrawService.withdraw(memberId);

        // then
        MemberEntity withDrawnMember = memberReader.getMemberById(memberId);
        assertThat(withDrawnMember.getMemberStatus()).isEqualTo(DELETED);
        assertThat(withDrawnMember.getDeletedAt()).isEqualTo(now);
    }
}