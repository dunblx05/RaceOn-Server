package com.parting.dippin.api.report.service;

import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.domain.report.MemberReportRegister;
import com.parting.dippin.domain.report.service.MemberReportSaveService;
import com.parting.dippin.entity.report.MemberReportEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReportService {
    private final MemberReader memberReader;
    private final MemberReportSaveService memberReportSaveService;

    public MemberReportEntity reportMember(int reporterId, int reportedMemberId) {
        MemberReportRegister memberReportRegister = new MemberReportRegister(reporterId, reportedMemberId);

        return memberReportRegister.report(memberReader, memberReportSaveService);
    }
}
