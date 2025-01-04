package com.parting.dippin.domain.report;

import static com.parting.dippin.domain.report.exception.ReportCodeAndMessage.INVALID_REPORTED_MEMBER_ID;

import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.domain.report.exception.ReportTypeException;
import com.parting.dippin.domain.report.service.MemberReportSaveService;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.report.MemberReportEntity;

public class MemberReportRegister {

    private final int reporterId;
    private final int reportedMemberId;

    public MemberReportRegister(int reporterId, int reportedMemberId) {
        this.reporterId = reporterId;
        this.reportedMemberId = reportedMemberId;
    }

    public MemberReportEntity report(
            MemberReader memberReader,
            MemberReportSaveService memberReportSaveService
    ) {
        MemberEntity reporter = memberReader.getMemberById(reporterId);

        MemberEntity reportedMember = memberReader.getMemberById(reportedMemberId, () -> ReportTypeException.from(INVALID_REPORTED_MEMBER_ID));

        return memberReportSaveService.save(reporter, reportedMember);
    }
}
