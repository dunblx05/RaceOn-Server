package com.parting.dippin.docs.token;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.token.TokenController;
import com.parting.dippin.api.token.dto.PostTokenReqDto;
import com.parting.dippin.api.token.service.TokenService;
import com.parting.dippin.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class TokenControllerDocsTest extends RestDocsSupport {

    @MockBean
    private TokenService tokenService;

    @Override
    protected Object initController() {
        tokenService = mock(TokenService.class);

        return new TokenController(tokenService);
    }

    @DisplayName("토큰 전송")
    @Test
    void saveToken() throws Exception {
        // given
        PostTokenReqDto dto = PostTokenReqDto.builder().token("test").build();

        willDoNothing().given(tokenService).saveToken(1, "test");

        // when
        ResultActions result = this.mockMvc.perform(
            RestDocumentationRequestBuilders.post("/tokens/{memberId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );

        // then
        result
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("memberId").description("유저의 memberId")
                    ),
                    requestFields(
                        fieldWithPath("token")
                            .type(JsonFieldType.STRING)
                            .description("FCM 토큰")
                    ),
                    responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드")
                    )
                )
            );
    }
}
