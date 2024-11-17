package com.parting.dippin.domain.friend.dto;

import com.parting.dippin.entity.friends.FriendsEntity;
import com.parting.dippin.entity.member.MemberEntity;
import lombok.Getter;

@Getter
public class FriendDto {

    Integer friendId;
    String friendNickname;

    public FriendDto(Integer friendId, String friendNickname) {
        this.friendId = friendId;
        this.friendNickname = friendNickname;
    }

    public FriendDto(MemberEntity memberEntity, FriendsEntity friendsEntity) {
        this.friendId = friendsEntity.getFriendId();
        this.friendNickname = memberEntity.getNickname();
    }
}
