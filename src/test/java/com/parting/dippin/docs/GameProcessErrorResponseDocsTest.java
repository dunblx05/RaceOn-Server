package com.parting.dippin.docs;


import static com.parting.dippin.docs.error_response.GameSpecificCodeAndMessageExtractor.extractGameStartCodeAndMessage;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.core.exception.CodeAndMessage;
import com.parting.dippin.core.exception.CommonCodeAndMessage;
import com.parting.dippin.docs.error_response.CodeAndMessageResponseFieldsSnippet;
import com.parting.dippin.docs.error_response.CommonErrorCodeController;
import com.parting.dippin.docs.error_response.GameProcessErrorCodeController;
import com.parting.dippin.domain.game.exception.GameCodeAndMessage;
import com.parting.dippin.domain.game.exception.docs.GameProcessException;
import java.util.Arrays;
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
 * @see CommonErrorCodeController
 */
@DisplayName("[DocumentationTest] 게임 진행 에러코드 테스트")
class GameProcessErrorResponseDocsTest extends RestDocsExceptionSupport {

    @Override
    protected Object initController() {
        return new GameProcessErrorCodeController();
    }

    @Test
    @DisplayName("게임 진행에서 사용된 응답 코드를 조회하면 200 OK를 반환한다.")
    void errorCodeResponseTest() throws Exception {
        CodeAndMessage[] codeAndMessages
                = extractGameStartCodeAndMessage(GameCodeAndMessage.values(), GameProcessException.class);
        CodeAndMessage[] gameCodeAndMessages = Arrays.copyOf(codeAndMessages, codeAndMessages.length + 1);
        gameCodeAndMessages[gameCodeAndMessages.length - 1] = CommonCodeAndMessage.BAD_REQUEST;

        FieldDescriptor[] descriptors = convertToFieldDescriptorArray(gameCodeAndMessages);
        CodeAndMessageResponseFieldsSnippet codeAndMessageResponseFieldsSnippet = getCodeAndMessageResponseFieldsSnippet(
                "common-error-code-response",
                beneathPath(path),
                null,
                descriptors
        );

        ResultActions result = this.mockMvc.perform(
                get("/api/specs/game-process-error-response")
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