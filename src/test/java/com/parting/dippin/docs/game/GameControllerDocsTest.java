package com.parting.dippin.docs.game;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.CREATED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.game.GameController;
import com.parting.dippin.api.game.dto.GameGeneratedInfoDto;
import com.parting.dippin.api.game.dto.PostGameReqDto;
import com.parting.dippin.api.game.service.GameMessageService;
import com.parting.dippin.api.game.service.GameService;
import com.parting.dippin.docs.RestDocsExceptionSupport;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class GameControllerDocsTest extends RestDocsExceptionSupport {

    GameService gameService;
    GameMessageService gameMessageService;

    @Override
    protected Object initController() {
        gameService = mock(GameService.class);
        gameMessageService = mock(GameMessageService.class);

        return new GameController(gameService, gameMessageService);
    }

    @DisplayName("게임 초대 요청")
    @Test
    void requestGame() throws Exception {
        // given
        PostGameReqDto postGameReqDto = new PostGameReqDto();
        postGameReqDto.setFriendId(2);
        postGameReqDto.setDistance(3.0);
        postGameReqDto.setTimeLimit(30);

        GameGeneratedInfoDto gameGeneratedInfoDto
                = new GameGeneratedInfoDto(
                1,
                1,
                "test",
                2,
                "test2",
                3.0,
                30
        );

        given(gameService.requestGame(eq(1), any(PostGameReqDto.class)))
                .willReturn(gameGeneratedInfoDto);
        willDoNothing().given(gameMessageService).sendInvitationMessage(gameGeneratedInfoDto);

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(postGameReqDto))
        );

        // then
        verifyResponse(result, CREATED);
        result
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("friendId").type(JsonFieldType.NUMBER)
                                                .description("게임 초대할 멤버코드"),
                                        fieldWithPath("distance").type(JsonFieldType.NUMBER).description("거리 (km)"),
                                        fieldWithPath("timeLimit").type(JsonFieldType.NUMBER)
                                                .description("제한시간 (분)")
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                        fieldWithPath("data.gameInfo").type(JsonFieldType.OBJECT)
                                                .description("+ 게임 정보"),
                                        fieldWithPath("data.gameInfo.gameId").type(JsonFieldType.NUMBER)
                                                .description("++ 게임 번호"),
                                        fieldWithPath("data.gameInfo.requestMemberId").type(JsonFieldType.NUMBER)
                                                .description("++ 게임 초대 요청한 멤버 아이디"),
                                        fieldWithPath("data.gameInfo.requestNickname").type(JsonFieldType.STRING)
                                                .description("++ 게임 초대 요청한 멤버 닉네임"),
                                        fieldWithPath("data.gameInfo.receivedMemberId").type(JsonFieldType.NUMBER)
                                                .description("++ 게임 초대 받은 멤버 아이디"),
                                        fieldWithPath("data.gameInfo.receivedNickname").type(JsonFieldType.STRING)
                                                .description("++ 게임 초대 받은 멤버 닉네임"),
                                        fieldWithPath("data.gameInfo.distance").type(JsonFieldType.NUMBER)
                                                .description("++ 게임 런닝 거리 (km) 소수점 3자리"),
                                        fieldWithPath("data.gameInfo.timeLimit").type(JsonFieldType.NUMBER)
                                                .description("++ 게임 제한 시간 (분)")
                                )
                        )
                );
    }
}
