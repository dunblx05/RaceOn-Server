package com.parting.dippin.friend;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.friend.FriendController;
import com.parting.dippin.api.friend.service.FriendReader;
import com.parting.dippin.api.friend.service.FriendService;
import com.parting.dippin.domain.friend.dto.FriendDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(controllers = FriendController.class)
@WithMockUser
class FriendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FriendReader friendReader;

    @MockBean
    private FriendService friendService;

    @DisplayName("친구 목록 조회")
    @Test
    void getFriends() throws Exception {
        // given
        List<FriendDto> friends = new ArrayList<>();
        friends.add(new FriendDto(2, "test2"));
        friends.add(new FriendDto(3, "test3"));

        given(friendReader.getFriends(1)).willReturn(friends);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/friends"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-friends",
                responseFields(
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                    fieldWithPath("data.friends").type(JsonFieldType.ARRAY).description("+ 친구 목록 데이터"),
                    fieldWithPath("data.friends[].friendId").type(JsonFieldType.NUMBER)
                        .description("++ 친구 ID"),
                    fieldWithPath("data.friends[].friendNickname").type(JsonFieldType.STRING)
                        .description("++ 친구 닉네임")
                )));
    }
}
