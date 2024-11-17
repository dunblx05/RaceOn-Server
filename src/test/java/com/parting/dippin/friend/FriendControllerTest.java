package com.parting.dippin.friend;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.parting.dippin.api.friend.FriendController;
import com.parting.dippin.domain.friend.dao.IFriendDAO;
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
    private IFriendDAO friendDAO;

    @DisplayName("친구 목록 조회")
    @Test
    void getFriendList() throws Exception {
        // given
        List<FriendDto> friendDtoList = new ArrayList<>();
        friendDtoList.add(new FriendDto(2, "test2"));
        friendDtoList.add(new FriendDto(3, "test3"));

        given(friendDAO.getFriendList(1)).willReturn(friendDtoList);

        // when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/friend/{memberId}", 1))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("get-friends",
                pathParameters(parameterWithName("memberId").description("회원 ID")),
                responseFields(fieldWithPath("data").type(JsonFieldType.ARRAY).description("친구 목록 데이터"),
                    fieldWithPath("data[].friendId").type(JsonFieldType.NUMBER).description("-- 친구 ID"),
                    fieldWithPath("data[].friendNickname").type(JsonFieldType.STRING).description("-- 친구 닉네임")
                )));
    }
}
