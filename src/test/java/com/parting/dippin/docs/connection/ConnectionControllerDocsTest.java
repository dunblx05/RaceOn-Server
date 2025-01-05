package com.parting.dippin.docs.connection;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.OK;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.connection.ConnectionController;
import com.parting.dippin.api.connection.service.ConnectionService;
import com.parting.dippin.docs.RestDocsExceptionSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

class ConnectionControllerDocsTest extends RestDocsExceptionSupport {

    private ConnectionService connectionService;

    @Override
    protected Object initController() {
        connectionService = mock(ConnectionService.class);

        return new ConnectionController(connectionService);
    }

    @DisplayName("접속 상태를 갱신한다.")
    @Test
    void updateConnectionStatus() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.put("/connection-status")
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(AUTHORIZATION, ACCESS_TOKEN)
        );

        // then
        verifyResponse(result, OK);
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                defaultResponseFields()
                        )
                );
    }
}