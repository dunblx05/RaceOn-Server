package com.parting.dippin.domain.report.service;

import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.report.MemberReportEntity;
import com.parting.dippin.entity.report.repository.MemberReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReportSaveService {

    private final MemberReportRepository reportRepository;

    public MemberReportEntity save(
            MemberEntity reporter,
            MemberEntity reportedMember
    ) {
        MemberReportEntity reportEntity = MemberReportEntity.builder()
                .reporter(reporter)
                .reportedMember(reportedMember)
                .build();

        return reportRepository.save(reportEntity);
    }
}
