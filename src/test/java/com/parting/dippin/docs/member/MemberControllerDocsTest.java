package com.parting.dippin.docs.member;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.BAD_REQUEST;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.CREATED;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_USER_TOKEN;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.OK;
import static com.parting.dippin.domain.member.exception.MemberCodeAndMessage.EXIST_MEMBER;
import static com.parting.dippin.entity.member.enums.SocialProvider.KAKAO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.api.member.MemberController;
import com.parting.dippin.api.member.service.MemberService;
import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.docs.RestDocsExceptionSupport;
import com.parting.dippin.domain.member.dto.MemberRegisterDto;
import com.parting.dippin.domain.member.exception.MemberTypeException;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.domain.member.service.MemberWithdrawService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class MemberControllerDocsTest extends RestDocsExceptionSupport {

    private MemberService memberService;
    private MemberReader memberReader;
    private MemberWithdrawService memberWithdrawService;

    @Override
    protected Object initController() {
        memberService = mock(MemberService.class);
        memberReader = mock(MemberReader.class);
        memberWithdrawService = mock(MemberWithdrawService.class);

        return new MemberController(
                memberService,
                memberReader,
                memberWithdrawService
        );
    }

    @DisplayName("멤버코드 조회")
    @Test
    void getMemberCode() throws Exception {
        // given
        String memberCode = "123Ad2";

        given(memberReader.getMemberCode(1)).willReturn(memberCode);

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/members/{memberId}/member-code", 1)
                        .header(AUTHORIZATION, ACCESS_TOKEN)
        );

        // then
        verifyResponse(result, OK);
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("memberId").description("조회하고자 하는 유저의 memberId")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                        fieldWithPath("data.memberCode").type(JsonFieldType.STRING)
                                                .description("+ 멤버 코드")
                                )
                        )
                );
    }

    @DisplayName("회원 가입을 할 수 있다.")
    @Test
    void signUp() throws Exception {
        // given
        int memberId = 1;

        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .idToken("idToken")
                .socialProvider(KAKAO)
                .nickname("nickname")
                .profileImageUrl("https://content.url")
                .build();

        GetJwtResDto jwtDto = GetJwtResDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .memberId(memberId)
                .build();

        given(memberService.signUp(any())).willReturn(jwtDto);
        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRegisterDto))
        );

        // then
        verifyResponse(result, CREATED);
        result
                .andDo(print())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("idToken")
                                        .type(JsonFieldType.STRING)
                                        .description("Social Login SDK를 통해 받은 id token")
                                        .attributes(constraints("JWT")),
                                fieldWithPath("socialProvider").type(JsonFieldType.STRING)
                                        .description("소셜 로그인 제공자(KAKAO, APPLE, GOOGLE")
                                        .attributes(constraints("KAKAO, APPLE, GOOGLE 중 하나여야하며 대문자만 허용한다.")),
                                fieldWithPath("nickname").type(JsonFieldType.STRING)
                                        .description("닉네임")
                                        .attributes(constraints("최대 20자 까지 허용된다.\n카카오 로그인을 통해 닉네임을 제공받았을 경우 해당된다."))
                                        .optional(),
                                fieldWithPath("profileImageUrl").type(JsonFieldType.STRING)
                                        .description("프로필 이미지 url")
                                        .attributes(constraints(
                                                "1. KAKAO로그인을 통해 프로필 이미지 주소를 제공받았을 경우 해당된다.\n URL 형식이여야한다."))
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.accessToken").type(STRING).description("액세스 토큰"),
                                fieldWithPath("data.refreshToken").type(STRING).description("리프레시 토큰"),
                                fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("유저 id")
                        )
                ))
                .andExpect(status().isCreated());
    }

    @DisplayName("닉네임과 프로필 이미지 없이도 회원 가입을 할 수 있다.")
    @Test
    void signUpWithoutNicknameAndProfileImageUrl() throws Exception {
        // given
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .idToken("idToken")
                .socialProvider(KAKAO)
                .build();

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/members")
                        .header(AUTHORIZATION, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRegisterDto))
        );

        // then
        verifyResponse(result, CREATED);
        result
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("회원가입을 진행할 때, idToken은 필수이다.")
    @Test
    void signUpWithoutIdTokenThenReturn400Code() throws Exception {
        // given
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .socialProvider(KAKAO)
                .build();

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/members")
                        .header(AUTHORIZATION, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRegisterDto))
        );

        // then
        verifyResponse(result, BAD_REQUEST);
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원가입을 진행할 때, 소셜 제공자는 필수이다.")
    @Test
    void signUpWithoutSocialProviderThenReturn400Code() throws Exception {
        // given
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .idToken("idToken")
                .build();

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/members")
                        .header(AUTHORIZATION, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRegisterDto))
        );

        // then
        verifyResponse(result, BAD_REQUEST);
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원가입을 진행할 때, 잘못된 idToken을 전달하는 경우 403을 반환한다.")
    @Test
    void signUpWithInvalidIdTokenThenReturn403Code() throws Exception {
        // given
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .socialProvider(KAKAO)
                .idToken("idToken")
                .build();

        given(memberService.signUp(any()))
                .willThrow(CommonException.from(INVALID_USER_TOKEN));

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/members")
                        .header(AUTHORIZATION, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRegisterDto))
        );

        // then
        verifyResponse(result, INVALID_USER_TOKEN);
        result
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @DisplayName("이미 가입된 유저의 토큰으로 회원가입을 시도할 경우 400을 반환한다.")
    @Test
    void signUpWithExistingUserTokenThenReturn400Code() throws Exception {
        // given
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .socialProvider(KAKAO)
                .idToken("idToken")
                .build();

        given(memberService.signUp(any()))
                .willThrow(MemberTypeException.from(EXIST_MEMBER));

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/members")
                        .header(AUTHORIZATION, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRegisterDto))
        );

        // then
        verifyResponse(result, EXIST_MEMBER);
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
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
