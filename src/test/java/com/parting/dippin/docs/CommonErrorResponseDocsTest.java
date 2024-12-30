package com.parting.dippin.docs;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.core.exception.CommonCodeAndMessage;
import com.parting.dippin.docs.error_response.CodeAndMessageResponseFieldsSnippet;
import com.parting.dippin.docs.error_response.CommonErrorCodeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;

/***
 * 공통 에러 응답을 문서화하기 위한 테스트이다.
 * 해당 테스트는 실제로 테스트를 한다기 보다는 공통 에러 응답을
 * RestDocs 에서 표 형태로 보여주기 위함으로 이해하면 된다.
 *
 * @author 정민욱
 * @see com.parting.dippin.docs.error_response.CommonErrorCodeController
 */
@DisplayName("[DocumentationTest] 에러코드 API 테스트")
public class CommonErrorResponseDocsTest extends RestDocsExceptionSupport {

    @Override
    protected Object initController() {
        return new CommonErrorCodeController();
    }

    @Test
    @DisplayName("프로젝트에서 사용된 응답 코드를 조회하면 200 OK를 반환한다.")
    void errorCodeResponseTest() throws Exception {
        CommonCodeAndMessage[] values = CommonCodeAndMessage.values();
        FieldDescriptor[] descriptors = convertToFieldDescriptorArray(values);
        CodeAndMessageResponseFieldsSnippet codeAndMessageResponseFieldsSnippet = getCodeAndMessageResponseFieldsSnippet(
                "common-error-code-response",
                beneathPath(path),
                null,
                descriptors
        );

        ResultActions result = this.mockMvc.perform(
                get("/api/specs/common-error-response")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andDo(document(
                        identifier,
                        codeAndMessageResponseFieldsSnippet
                ))
                .andExpect(status().isOk())
                .andDo(print());
    }
}