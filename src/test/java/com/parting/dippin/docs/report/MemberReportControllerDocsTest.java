package com.parting.dippin.docs.report;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.CREATED;
import static com.parting.dippin.domain.report.exception.ReportCodeAndMessage.INVALID_REPORTED_MEMBER_ID;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.report.MemberReportController;
import com.parting.dippin.api.report.dto.ReportMemberRequestDto;
import com.parting.dippin.api.report.service.MemberReportService;
import com.parting.dippin.docs.RestDocsExceptionSupport;
import com.parting.dippin.domain.report.exception.ReportTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class MemberReportControllerDocsTest extends RestDocsExceptionSupport {

    private MemberReportService memberReportService;

    @Override
    protected Object initController() {
        memberReportService = mock(MemberReportService.class);

        return new MemberReportController(memberReportService);
    }

    @DisplayName("유저 신고")
    @Test
    void reportMember() throws Exception {
        // given
        int reporterId = 1;
        int reportedMemberId = 2;
        ReportMemberRequestDto requestDtO = new ReportMemberRequestDto(reportedMemberId);

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/report/members", reporterId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDtO))
                        .header(AUTHORIZATION, ACCESS_TOKEN)
        );

        // then
        verifyResponse(result, CREATED);
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("reportedMemberId")
                                                .type(JsonFieldType.NUMBER)
                                                .description(
                                                        "신고하고자 하는 유저의 memberId")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                                )
                        )
                );
    }

    @DisplayName("존재하지 않는 유저를 신고하는 경우 400을 리턴한다.")
    @Test
    void reportMemberWithNonExistMemberIdThenReturn400Code() throws Exception {
        // given
        int reporterId = 1;
        int reportedMemberId = 2;
        ReportMemberRequestDto requestDtO = new ReportMemberRequestDto(reportedMemberId);

        given(memberReportService.reportMember(reporterId, reportedMemberId))
                .willThrow(ReportTypeException.from(INVALID_REPORTED_MEMBER_ID));

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/report/members", reporterId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDtO))
                        .header(AUTHORIZATION, ACCESS_TOKEN)
        );

        // then
        verifyResponse(result, INVALID_REPORTED_MEMBER_ID);
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
