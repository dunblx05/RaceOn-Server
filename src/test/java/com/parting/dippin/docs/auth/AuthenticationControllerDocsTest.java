package com.parting.dippin.docs.auth;

import static com.parting.dippin.api.auth.exception.AuthenticationCodeAndMessage.USER_NOT_REGISTERED;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.BAD_REQUEST;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.CREATED;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_USER_TOKEN;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.OK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.auth.AuthenticationController;
import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.api.auth.dto.PostLoginReqDto;
import com.parting.dippin.api.auth.exception.AuthenticationCodeAndMessage;
import com.parting.dippin.api.auth.exception.AuthenticationTypeException;
import com.parting.dippin.api.auth.service.AuthenticationService;
import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.docs.RestDocsExceptionSupport;
import com.parting.dippin.docs.RestDocsSupport;
import com.parting.dippin.entity.member.enums.SocialProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class AuthenticationControllerDocsTest extends RestDocsExceptionSupport {

    private final AuthenticationService authenticationService = mock(AuthenticationService.class);

    @Override
    protected Object initController() {
        return new AuthenticationController(authenticationService);
    }

    @DisplayName("임시 로그인 테스트")
    @Test
    void tempLogin() throws Exception {
        // Given
        int memberId = 1;
        GetJwtResDto jwtDto = GetJwtResDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .memberId(memberId)
                .build();

        given(authenticationService.login(memberId)).willReturn(jwtDto);

        // When & Then
        mockMvc.perform(post("/auth/temp/login")
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

        // When
        ResultActions result = mockMvc.perform(post("/auth/reissue")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, REFRESH_TOKEN)
        );

        // Then
        verifyResponse(result, OK);
        result
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

    @DisplayName("소셜 로그인 테스트")
    @Test
    void socialLogin() throws Exception {
        // Given
        int memberId = 1;
        PostLoginReqDto requestDto = PostLoginReqDto.builder()
                .idToken("idToken")
                .socialProvider(SocialProvider.KAKAO)
                .build();

        GetJwtResDto jwtDto = GetJwtResDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .memberId(memberId)
                .build();

        given(authenticationService.login(any())).willReturn(jwtDto);

        // When
        ResultActions result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        // Then
        verifyResponse(result, CREATED);
        result
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("idToken")
                                        .type(JsonFieldType.STRING)
                                        .description(
                                                "SDK를 통해 로그인을 진행한 후, 받은 response에서 추출한 id_token"
                                        ),
                                fieldWithPath("socialProvider").type(JsonFieldType.STRING)
                                        .description("소셜 제공자(KAKAO,APPLE,GOOGLE)")
                                        .attributes(
                                                constraints("KAKAO, APPLE, GOOGLE 중 하나여야 하며 대문자로 보내야한다.")
                                        )
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

    @DisplayName("잘못된 소셜 Id를 통한 소셜 로그인 테스트")
    @Test
    void socialLoginWithInvalidSocialId() throws Exception {
        // Given
        int memberId = 1;
        GetJwtResDto jwtDto = GetJwtResDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .memberId(memberId)
                .build();

        given(authenticationService.login(any())).willReturn(jwtDto);

        // When
        ResultActions result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                                "idToken":"idToken",
                                "socialProvider": "minuk"

                        }
                        """)
        );

        // Then
        verifyResponse(result, BAD_REQUEST);
        result
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("잘못된 idToken 을 통한 소셜 로그인 테스트")
    @Test
    void socialLoginWithInvalidToken() throws Exception {
        // Given
        int memberId = 1;
        PostLoginReqDto requestDto = PostLoginReqDto.builder()
                .idToken("idToken")
                .socialProvider(SocialProvider.KAKAO)
                .build();

        given(authenticationService.login(any()))
                .willThrow(CommonException.from(INVALID_USER_TOKEN));

        // When
        ResultActions result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        // Then
        verifyResponse(result, INVALID_USER_TOKEN);
        result
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("회원가입이 필요한 유저의 경우 에러 응답을 반환한다.")
    @Test
    void socialLoginWithNotRegisteredUserId() throws Exception {
        // Given
        int memberId = 1;
        PostLoginReqDto requestDto = PostLoginReqDto.builder()
                .idToken("idToken")
                .socialProvider(SocialProvider.KAKAO)
                .build();

        given(authenticationService.login(any()))
                .willThrow(AuthenticationTypeException.from(USER_NOT_REGISTERED));

        // When
        ResultActions result = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto))
        );

        // Then
        verifyResponse(result, USER_NOT_REGISTERED);
        result
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
