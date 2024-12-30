package com.parting.dippin.docs;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.parting.dippin.core.exception.CodeAndMessage;
import com.parting.dippin.docs.error_response.CodeAndMessageResponseFieldsSnippet;
import java.util.Arrays;
import java.util.Map;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.restdocs.snippet.Attributes.Attribute;
import org.springframework.test.web.servlet.ResultActions;

/**
 * RestDocs Test 중 Exception에 대한 테스트 시 필요한 메서드들을 모아놓은 클래스
 *
 * @Author 정민욱
 * @see com.parting.dippin.docs.CommonErrorResponseDocsTest
 */
public abstract class RestDocsExceptionSupport extends RestDocsSupport {

    protected final String path = "errorCodes";
    protected final String identifier = "{class-name}/{method-name}";
    protected final String code = "code";
    protected final String message = "message";
    protected final String errorCodeResponse = "error-code-response";

    protected FieldDescriptor[] convertToFieldDescriptorArray(
            CodeAndMessage[] errorCodeAndMessages
    ) {
        return Arrays.stream(errorCodeAndMessages)
                .map(this::getFieldDescriptor)
                .toArray(FieldDescriptor[]::new);
    }

    protected FieldDescriptor getFieldDescriptor(CodeAndMessage codeAndMessages) {
        return fieldWithPath(codeAndMessages.name())
                .attributes(
                        getAttribute(code, codeAndMessages.code()),
                        getAttribute(message, codeAndMessages.message())
                )
                .type(JsonFieldType.OBJECT);
    }

    /**
     * 예외 응답 결과를 편하게 검증하기위한 메서드이다. result와 CodeAndMessage를 받아 결과를 자동 검증한다.
     *
     * @param result
     * @param codeAndMessage
     * @throws Exception
     */
    protected void verifyResponse(ResultActions result, CodeAndMessage codeAndMessage) throws Exception {
        result
                .andExpect(jsonPath("$.success").value(codeAndMessage.isSuccess()))
                .andExpect(jsonPath("$.code").value(codeAndMessage.code()))
                .andExpect(jsonPath("$.message").value(codeAndMessage.message()));
    }

    /**
     * 공통 응답을 표형식으로 나타내기 위한 CustomSnippet 을 반환한다.
     *
     * @param type
     * @param subsectionExtractor
     * @param attributes
     * @param descriptors
     * @return CodeAndMessageResponseFieldsSnippet
     */
    protected CodeAndMessageResponseFieldsSnippet getCodeAndMessageResponseFieldsSnippet(
            String type,
            PayloadSubsectionExtractor<?> subsectionExtractor,
            Map<String, Object> attributes,
            FieldDescriptor... descriptors
    ) {
        return new CodeAndMessageResponseFieldsSnippet(
                type,
                subsectionExtractor,
                attributes,
                Arrays.asList(descriptors),
                true
        );
    }

    /***
     * Custom Attribute를 편하게 추가할 수 있게 도와주는 메서드이다.
     *
     * @see #getFieldDescriptor
     * @param key
     * @param value
     * @return Attribute
     */
    protected Attribute getAttribute(
            String key,
            Object value
    ) {
        return key(key)
                .value(value);
    }
}
