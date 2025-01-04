package com.parting.dippin.api.report;

import com.parting.dippin.api.report.dto.ReportMemberRequestDto;
import com.parting.dippin.api.report.service.MemberReportService;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberReportController {

    private final MemberReportService memberReportService;

    @PostMapping("/report/members")
    public BaseResponse<Void> reportMember(
            @LoggedInMemberId Integer reporterId,
            @RequestBody ReportMemberRequestDto reportMemberRequestDto
    ) {
        memberReportService.reportMember(reporterId, reportMemberRequestDto.getReportedMemberId());

        return BaseResponse.created();
    }
}
