package com.parting.dippin.auth;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(username = "1")
@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureRestDocs
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authService;

    @DisplayName("로그인")
    @Test
    void login() throws Exception {
        // Given
        GetJwtResDto jwtDto = GetJwtResDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        int memberId = 1;
        given(authService.login(memberId)).willReturn(jwtDto);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .queryParam("memberId", String.valueOf(memberId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("auth-login",
                        queryParameters(
                                parameterWithName("memberId").description("로그인 하고자 하는 memberId")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰")
                        )
                ));
    }


    @DisplayName("토큰 재발행")
    @Test
    void reissue() throws Exception {
        // Given
        GetJwtResDto jwtDto = GetJwtResDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        int memberId = 1;
        given(authService.reissue(memberId)).willReturn(jwtDto);

        // When & Then
        mockMvc.perform(post("/auth/reissue")
                        .with(csrf())
                        .header("X-AUTH-TOKEN", "someToken") // Assuming you're using a custom header for authentication
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("auth-reissue",
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("새 액세스 토큰"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("새 리프레시 토큰")
                        )
                ));
    }
}