package com.parting.dippin.domain.friend;

import com.parting.dippin.domain.friend.service.FriendAddingService;
import com.parting.dippin.domain.friend.service.FriendValidationService;
import lombok.Getter;

@Getter
public class Friend {

    private final int memberId;
    private final int friendId;

    public Friend(int memberId, int friendId) {
        this.memberId = memberId;
        this.friendId = friendId;
    }

    public void addFriend(FriendValidationService friendValidationService, FriendAddingService friendAddingService) {

        if (isAlreadyFriend(friendValidationService)) {
            // TODO - THROW ERROR
            throw new RuntimeException();
        }

        friendAddingService.addFriend(this);
    }

    private boolean isAlreadyFriend(FriendValidationService friendValidationService) {
        return friendValidationService.isAlreadyFriend(memberId, friendId);
    }
}
