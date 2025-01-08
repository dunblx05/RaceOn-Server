package com.parting.dippin.docs.friend;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.BAD_REQUEST;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.CREATED;
import static com.parting.dippin.domain.friend.exception.FriendCodeAndMessage.ALREADY_FRIEND_EXCEPTION;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.OK;
import static com.parting.dippin.domain.friend.exception.FriendCodeAndMessage.NOT_FRIENDS_EXCEPTION;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.friend.FriendController;
import com.parting.dippin.api.friend.dto.PostFriendsReqDto;
import com.parting.dippin.api.friend.service.FriendReader;
import com.parting.dippin.api.friend.service.FriendService;
import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.api.friend.dto.DeleteFriendReqDto;
import com.parting.dippin.docs.RestDocsExceptionSupport;
import com.parting.dippin.domain.friend.dto.FriendDto;
import com.parting.dippin.domain.friend.exception.FriendTypeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes.Attribute;
import org.springframework.test.web.servlet.ResultActions;

class FriendControllerDocsTest extends RestDocsExceptionSupport {

    private FriendReader friendReader;
    private FriendService friendService;

    @Override
    protected Object initController() {
        friendReader = mock(FriendReader.class);
        friendService = mock(FriendService.class);

        return new FriendController(friendReader, friendService);
    }

    @DisplayName("친구 목록 조회")
    @Test
    void getFriends() throws Exception {
        // given
        List<FriendDto> friends = new ArrayList<>();
        friends.add(new FriendDto(2, "test2", "https://profile1.image.url", LocalDateTime.now()));
        friends.add(new FriendDto(3, "test3", "https://profile2.image.url", LocalDateTime.now()));

        given(friendReader.getFriends(1)).willReturn(friends);

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/friends")
                        .header(AUTHORIZATION, ACCESS_TOKEN)
        );

        // then
        verifyResponse(result, OK);
        result
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                        fieldWithPath("data.friends").type(JsonFieldType.ARRAY)
                                                .description("+ 친구 목록 데이터"),
                                        fieldWithPath("data.friends[].friendId").type(JsonFieldType.NUMBER)
                                                .description("++ 친구 ID"),
                                        fieldWithPath("data.friends[].friendNickname").type(JsonFieldType.STRING)
                                                .description("++ 친구 닉네임"),
                                        fieldWithPath("data.friends[].profileImageUrl").type(JsonFieldType.STRING)
                                                .description("++ 친구 프로필 이미지 URL"),
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

        PostFriendsReqDto postFriendsReqDto = new PostFriendsReqDto("J13D6E");

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/friends")
                        .header(AUTHORIZATION, ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postFriendsReqDto))
        );

        // then
        verifyResponse(result, CREATED);
        result
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("friendCode").type(JsonFieldType.STRING)
                                                .description("친구 추가할 멤버코드")
                                                .attributes(new Attribute("constraints", "영어 대문자, 숫자로 이루어진 6글자"))
                                ),
                                responseFields(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드")
                                )
                        )
                );
    }

    @DisplayName("본인의 id로 친구추가를 요청할 경우 400에러를 응답한다.")
    @Test
    void addFriendWithMyIdThenReturn400Code() throws Exception {
        // given
        String friendCode = "AAAAAA";
        PostFriendsReqDto dto = new PostFriendsReqDto(friendCode);

        doThrow(CommonException.from(BAD_REQUEST))
                .when(friendService).addFriend(1, friendCode);

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/friends")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        // then
        verifyResponse(result, BAD_REQUEST);
        result
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @DisplayName("이미 친구관계인 유저의 id로 친구추가를 시도할 경우 400코드를 반환한다.")
    @Test
    void addFriendWithFriendIdThenReturn400Code() throws Exception {
        // given
        String friendCode = "AAAAAA";
        PostFriendsReqDto dto = new PostFriendsReqDto(friendCode);

        doThrow(FriendTypeException.from(ALREADY_FRIEND_EXCEPTION))
                .when(friendService).addFriend(1, friendCode);

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.post("/friends")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );

        // then
        verifyResponse(result, ALREADY_FRIEND_EXCEPTION);
        result
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @DisplayName("친구 삭제 테스트")
    @Test
    void deleteFriend() throws Exception {
        // Given
        int memberId = 1;
        int friendId = 2;
        DeleteFriendReqDto deleteFriendReqDto = new DeleteFriendReqDto(friendId);

        // When & Then
        ResultActions result = mockMvc.perform(delete("/friends")
                .header(AUTHORIZATION, ACCESS_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFriendReqDto))
        );

        verifyResponse(result, OK);

        result
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("friendId").type(JsonFieldType.NUMBER)
                                        .description("삭제할 친구의 friendId")
                        ),
                        responseFields(
                                fieldWithPath("success").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("code").type(STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지")
                        )
                ));
    }

    @DisplayName("삭제하려는 대상이 친구상태가 아니면 400을 던진다.")
    @Test
    void deleteFriendWithDoesNotFriendIdThenReturn400Code() throws Exception {
        // given
        int memberId = 1;
        int friendId = 2;
        DeleteFriendReqDto deleteFriendReqDto = new DeleteFriendReqDto(friendId);

        willThrow(FriendTypeException.from(NOT_FRIENDS_EXCEPTION))
                .given(friendService)
                .deleteFriend(memberId, friendId);

        // when
        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.delete("/friends")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteFriendReqDto))
        );

        // then
        verifyResponse(result, NOT_FRIENDS_EXCEPTION);
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
