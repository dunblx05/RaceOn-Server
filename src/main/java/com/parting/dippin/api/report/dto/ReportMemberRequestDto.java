package com.parting.dippin.api.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportMemberRequestDto {
    int reportedMemberId;

    public ReportMemberRequestDto(int reportedMemberId) {
        this.reportedMemberId = reportedMemberId;
    }
}
