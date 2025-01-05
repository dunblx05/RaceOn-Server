package com.parting.dippin.docs.member;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.OK;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.member.MemberController;
import com.parting.dippin.docs.RestDocsExceptionSupport;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.domain.member.service.MemberWithdrawService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

class MemberControllerDocsTest extends RestDocsExceptionSupport {

    private MemberReader memberReader;
    private MemberWithdrawService memberWithdrawService;

    @Override
    protected Object initController() {
        memberReader = mock(MemberReader.class);
        memberWithdrawService = mock(MemberWithdrawService.class);

        return new MemberController(memberReader, memberWithdrawService);
    }

    @DisplayName("회원 탈퇴를 할 수 있다.")
    @Test
    void withdraw() throws Exception {
        // given
        int memberId = 1;

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/members/{memberId}", memberId)
                        .header(AUTHORIZATION, ACCESS_TOKEN)
        );

        // then
        verifyResponse(result, OK);
        result
                .andDo(print())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("memberId").description("수정하고자 하는 유저의 memberId")
                        ),
                        defaultResponseFields()
                ))
                .andExpect(status().isOk());
    }
}
