package com.parting.dippin.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.common.LoggedInMemberIdArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.restdocs.snippet.Attributes.Attribute;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {
    protected  RestDocumentationResultHandler restDocs;
    protected MockMvc mockMvc;
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(RestDocumentationContextProvider provider) {
        restDocs = restDocumentationResultHandler();

        this.mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .apply(documentationConfiguration(provider))
                .alwaysDo(MockMvcResultHandlers.print()) // print 적용
                .alwaysDo(restDocs)
                .setCustomArgumentResolvers(new LoggedInMemberIdArgumentResolver())
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

    protected Attributes.Attribute constraints(String constraints) {
        return new Attribute("constraints", constraints);
    }
}
