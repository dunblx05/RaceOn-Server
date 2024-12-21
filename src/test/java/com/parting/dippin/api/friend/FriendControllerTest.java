package com.parting.dippin.api.friend;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.api.friend.dto.PostFriendsReqDto;
import com.parting.dippin.api.friend.service.FriendReader;
import com.parting.dippin.api.friend.service.FriendService;
import com.parting.dippin.domain.friend.dto.FriendDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs
@WebMvcTest(controllers = FriendController.class)
@WithMockUser(username = "1")
@AutoConfigureMockMvc(addFilters = false)
class FriendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FriendReader friendReader;

    @MockBean
    private FriendService friendService;

    @DisplayName("친구 목록 조회")
    @Test
    void getFriends() throws Exception {
        // given
        List<FriendDto> friends = new ArrayList<>();
        friends.add(new FriendDto(2, "test2", LocalDateTime.now()));
        friends.add(new FriendDto(3, "test3", LocalDateTime.now()));

        given(friendReader.getFriends(1)).willReturn(friends);

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/friends")
        );

        // then
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("get-friends",
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                        fieldWithPath("data.friends").type(JsonFieldType.ARRAY)
                                                .description("+ 친구 목록 데이터"),
                                        fieldWithPath("data.friends[].friendId").type(JsonFieldType.NUMBER)
                                                .description("++ 친구 ID"),
                                        fieldWithPath("data.friends[].friendNickname").type(JsonFieldType.STRING)
                                                .description("++ 친구 닉네임"),
                                        fieldWithPath("data.friends[].lastActiveAt").type(JsonFieldType.STRING)
                                                .description("++ 최근 접속 시각"),
                                        fieldWithPath("data.friends[].playing").type(JsonFieldType.BOOLEAN)
                                                .description("++ 게임중 여부")
                                )
                        )
                );
    }

    @DisplayName("친구 추가")
    @Test
    void addFriend() throws Exception {
        // given
        willDoNothing().given(friendService).addFriend(1, "J13D6E");

        PostFriendsReqDto postFriendsReqDto = new PostFriendsReqDto();
        postFriendsReqDto.setFriendCode("J13D6E");

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/friends")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postFriendsReqDto))
        );

        // then
        result
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(
                document("post-friends",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("friendCode").type(JsonFieldType.STRING)
                            .description("친구 추가할 멤버코드")
                    ),
                    responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                        fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                    )
                )
            );
    }
}
