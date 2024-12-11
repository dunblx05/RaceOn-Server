package com.parting.dippin.api.friend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostFriendsReqDto {

    @NotBlank(message = "멤버 코드를 넣어주세요.")
    String friendCode;
}
