package com.parting.dippin.api.friend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteFriendReqDto {

    private int friendId;

    public DeleteFriendReqDto(int friendId) {
        this.friendId = friendId;
    }
}
