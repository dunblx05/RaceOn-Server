package com.parting.dippin.docs.auth;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.auth.AuthenticationController;
import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.api.auth.service.AuthenticationService;
import com.parting.dippin.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class AuthenticationControllerDocsTest extends RestDocsSupport {

    private final AuthenticationService authenticationService = mock(AuthenticationService.class);

    @Override
    protected Object initController() {
        return new AuthenticationController(authenticationService);
    }

    @DisplayName("로그인 테스트")
    @Test
    void login() throws Exception {
        // Given
        int memberId = 1;
        GetJwtResDto jwtDto = GetJwtResDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .memberId(memberId)
                .build();

        given(authenticationService.login(memberId)).willReturn(jwtDto);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .queryParam("memberId", String.valueOf(memberId))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(restDocs.document(
                        queryParameters(
                                parameterWithName("memberId").description("로그인 하고자 하는 memberId")
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.accessToken").type(STRING).description("액세스 토큰"),
                                fieldWithPath("data.refreshToken").type(STRING).description("리프레시 토큰"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("유저 id")
                        )
                ));
    }

    @DisplayName("토큰 재발급 테스트")
    @Test
    void reissue() throws Exception {
        // Given
        int memberId = 1;
        GetJwtResDto jwtDto = GetJwtResDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .memberId(memberId)
                .build();

        given(authenticationService.reissue(memberId)).willReturn(jwtDto);

        // When & Then
        mockMvc.perform(post("/auth/reissue")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, REFRESH_TOKEN)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(restDocs.document(
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("새 액세스 토큰"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("새 리프레시 토큰"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("유저 id")
                        )
                ));
    }
}
