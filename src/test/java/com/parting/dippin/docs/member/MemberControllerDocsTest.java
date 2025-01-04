package com.parting.dippin.docs.member;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.BAD_REQUEST;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.OK;
import static com.parting.dippin.domain.member.exception.MemberCodeAndMessage.FAILED_UPLOAD_IMAGE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.member.MemberController;
import com.parting.dippin.api.member.dto.PatchProfileImageResDto;
import com.parting.dippin.api.member.dto.PatchUpdateProfileReqDto;
import com.parting.dippin.docs.RestDocsExceptionSupport;
import com.parting.dippin.domain.member.exception.MemberTypeException;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.domain.member.service.MemberWithdrawService;
import com.parting.dippin.domain.member.service.ProfileUpdateService;
import java.net.URL;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class MemberControllerDocsTest extends RestDocsExceptionSupport {

    private MemberReader memberReader;
    private ProfileUpdateService profileUpdateService;
    private MemberWithdrawService memberWithdrawService;

    @Override
    protected Object initController() {
        memberReader = mock(MemberReader.class);
        profileUpdateService = mock(ProfileUpdateService.class);
        memberWithdrawService = mock(MemberWithdrawService.class);

        return new MemberController(memberReader, profileUpdateService, memberWithdrawService);
    }

    @DisplayName("프로필 이미지 수정 API")
    @Test
    void updateProfileImage() throws Exception {
        // given
        URL preSignedUrl = new URL("""
                https://s3.ap-northeast-2.amazonaws.com/testBucket/sub-bucket/
                3e77adb8-0595-4e78-95fd-710cd84e103d
                ?x-amz-acl=public-read&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20241221T113536Z
                &X-Amz-SignedHeaders=host&X-Amz-Expires=599&X-Amz-Credential=AKIAWQUOZLNTDLDFWYEC%2F20241221
                %2Fap-northeast-2%2Fs3%2Faws4_request
                &X-Amz-Signature=295dadf8920d3b1aad38c41d45a709754b071aaf8e5ad79c08fffdd3bd754119
                """);

        PatchProfileImageResDto patchProfileImageResDto = new PatchProfileImageResDto(preSignedUrl);
        given(profileUpdateService.updateProfileImage()).willReturn(patchProfileImageResDto);

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/members/{memberId}/profile-image", 1)
                        .header(AUTHORIZATION, ACCESS_TOKEN)
        );

        // then
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("memberId").description("수정하고자 하는 유저의 memberId")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data.preSignedUrl").type(JsonFieldType.STRING)
                                                .description("presigned Url"),
                                        fieldWithPath("data.contentUrl").type(JsonFieldType.STRING)
                                                .description("추후 생성될 이미지의 실제 URL이다.")
                                )
                        )
                );
    }

    @DisplayName("프로필 이미지 PresignedURL 발급 실패시 500에러를 던진다.")
    @Test
    void failedUploadImageThenReturn500code() throws Exception {
        // given
        given(profileUpdateService.updateProfileImage()).willThrow(
                MemberTypeException.from(FAILED_UPLOAD_IMAGE)
        );

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/members/{memberId}/profile-image", 1)
        );

        verifyResponse(result, FAILED_UPLOAD_IMAGE);

        // then
        result
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("프로필 수정 API")
    @Test
    void updateProfile() throws Exception {
        // given
        String newProfileUrl = "https://content.url";
        String testName = "test name";
        PatchUpdateProfileReqDto dto = PatchUpdateProfileReqDto.builder()
                .newProfileUrl(newProfileUrl)
                .username(testName)
                .build();

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/members/{memberId}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header(AUTHORIZATION, ACCESS_TOKEN)
        );

        // then
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("memberId").description("수정하고자 하는 유저의 memberId")
                                ),
                                requestFields(
                                        fieldWithPath("newProfileUrl")
                                                .type(JsonFieldType.STRING)
                                                .description(
                                                        "수정된 프로필 이미지 주소. 프로필 이미지 권한 요청 API의 response중 contentUrl에 해당한다.")
                                                .attributes(constraints("URL 형식"))
                                                .optional(),
                                        fieldWithPath("username").type(JsonFieldType.STRING)
                                                .description("닉네임")
                                                .attributes(constraints("20자 이하 문자열"))
                                                .optional()
                                ),
                                defaultResponseFields()
                        )
                );
    }

    @DisplayName("프로필 수정시 닉네임의 길이가 20자 초과라면 에러를 던진다.")
    @Test
    void updateProfileWithTooLongNicknameThenReturn400code() throws Exception {
        // given
        String testName = RandomStringUtils.randomAlphabetic(21);
        PatchUpdateProfileReqDto dto = PatchUpdateProfileReqDto.builder()
                .username(testName)
                .build();

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/members/{memberId}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        // then
        verifyResponse(result, BAD_REQUEST);
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("프로필 수정시 닉네임은 생략될 수 있다.")
    @Test
    void updateProfileWithoutNicknameThenReturn200code() throws Exception {
        // given
        String newProfileUrl = "https://content.url";
        PatchUpdateProfileReqDto dto = PatchUpdateProfileReqDto.builder()
                .newProfileUrl(newProfileUrl)
                .build();

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/members/{memberId}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        // then
        verifyResponse(result, OK);
        result
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("프로필 수정시 newProfileUrl이 URL형식이 아닐 경우 400코드를 반환한다..")
    @Test
    void updateProfileWithNotUrlFormatThenReturn400code() throws Exception {
        // given
        String newProfileUrl = "test-url";
        PatchUpdateProfileReqDto dto = PatchUpdateProfileReqDto.builder()
                .newProfileUrl(newProfileUrl)
                .build();

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/members/{memberId}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        // then
        verifyResponse(result, BAD_REQUEST);
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
