package com.parting.dippin.docs.error_response;

import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

/**
 *
 * CommonErrorResponseDocsTest 에서 에러 응답을 표 형식으로 나타내는데
 * 사용하기 위한 Custom Snippet 이다.
 *
 * @author 정민욱
 * @see com.parting.dippin.docs.CommonErrorResponseDocsTest
 *
 */
public class CodeAndMessageResponseFieldsSnippet extends AbstractFieldsSnippet {

    public CodeAndMessageResponseFieldsSnippet(
            String type,
            PayloadSubsectionExtractor<?> subsectionExtractor,
            Map<String, Object> attributes,
            List<FieldDescriptor> descriptors,
            boolean ignoreUndocumentedFields
    ) {
        super(type, descriptors, attributes, ignoreUndocumentedFields, subsectionExtractor);
    }

    @Override
    protected MediaType getContentType(Operation operation) {
        return operation.getResponse().getHeaders().getContentType();
    }

    @Override
    protected byte[] getContent(Operation operation) {
        return operation.getResponse().getContent();
    }
}
