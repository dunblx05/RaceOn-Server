package com.parting.dippin.api.report.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.parting.dippin.common.DatabaseTest;
import com.parting.dippin.entity.report.MemberReportEntity;
import com.parting.dippin.entity.report.repository.MemberReportRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTest
class MemberReportServiceTest {

    @Autowired
    MemberReportService memberReportService;

    @Autowired
    MemberReportRepository memberReportRepository;

    @DisplayName("유저를 신고한다.")
    @Test
    void reportMember() {
        // given
        int reporterId = 1;
        int reportedMemberId = 2;

        // when
        MemberReportEntity memberReportEntity = memberReportService.reportMember(reporterId, reportedMemberId);

        // then
        Optional<MemberReportEntity> reportOptional = memberReportRepository.findById(memberReportEntity.getReportId());

        assertThat(reportOptional).isPresent();

        MemberReportEntity report = reportOptional.get();
        assertThat(report.getReporter().getMemberId()).isEqualTo(reporterId);
        assertThat(report.getReportedMember().getMemberId()).isEqualTo(reportedMemberId);
    }
}