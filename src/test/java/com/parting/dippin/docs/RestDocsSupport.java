package com.parting.dippin.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.common.LoggedInMemberIdArgumentResolver;
import com.parting.dippin.core.exception.GlobalExceptionHandler;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.restdocs.snippet.Attributes.Attribute;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {

    protected static final String AUTHORIZATION = "Authorization";
    protected static final String ACCESS_TOKEN = "Bearer your access token";
    protected static final String REFRESH_TOKEN = "Bearer your refresh token";

    protected  RestDocumentationResultHandler restDocs;
    protected MockMvc mockMvc;
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(RestDocumentationContextProvider provider) {
        restDocs = restDocumentationResultHandler();
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet(); // Bean Validator 초기화

        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .apply(documentationConfiguration(provider))
                .alwaysDo(MockMvcResultHandlers.print()) // print 적용
                .alwaysDo(restDocs)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new LoggedInMemberIdArgumentResolver())
                .setValidator(validator)
                .build();
    }

    public RestDocumentationResultHandler restDocumentationResultHandler() {
        return MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",  // 문서 이름 설정
                preprocessRequest(prettyPrint()),     // pretty json 적용
                preprocessResponse(prettyPrint())     // pretty json 적용
        );
    }

    protected abstract Object initController();

    /**
     *   constraints 속성을 편하게 추가할 수 있게 도와주는 메서드이다.
     */
    protected Attributes.Attribute constraints(String constraints) {

        return new Attribute("constraints", constraints);
    }

    /**
     * success, code, message로 구성된 기본 Response Fields를 반환한다.
     * @author 정민욱
     */
    @NotNull
    protected static ResponseFieldsSnippet defaultResponseFields() {
        return responseFields(
                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
        );
    }
}
