package com.parting.dippin.api.friend.dto;

import com.parting.dippin.domain.friend.dto.FriendDto;
import java.util.List;
import lombok.Getter;

@Getter
public class GetFriendsResDto {
    List<FriendDto> friends;

    public GetFriendsResDto(List<FriendDto> friends) {
        this.friends = friends;
    }
}
