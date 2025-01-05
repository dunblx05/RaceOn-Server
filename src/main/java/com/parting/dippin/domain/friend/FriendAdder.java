package com.parting.dippin.domain.friend;

import static com.parting.dippin.domain.friend.exception.FriendCodeAndMessage.ALREADY_FRIEND_EXCEPTION;

import com.parting.dippin.domain.friend.exception.FriendTypeException;
import com.parting.dippin.domain.friend.service.FriendAddingService;
import com.parting.dippin.domain.friend.service.FriendValidationService;
import lombok.Getter;

@Getter
public class FriendAdder {

    private final int memberId;
    private final int friendId;

    public FriendAdder(int memberId, int friendId) {
        this.memberId = memberId;
        this.friendId = friendId;
    }

    public void addFriend(
            FriendValidationService friendValidationService,
            FriendAddingService friendAddingService
    ) {

        if (isAlreadyFriend(friendValidationService)) {
            throw FriendTypeException.from(ALREADY_FRIEND_EXCEPTION);
        }

        friendAddingService.addFriend(this);
    }

    private boolean isAlreadyFriend(FriendValidationService friendValidationService) {
        return friendValidationService.isLinkedFriend(memberId, friendId);
    }
}
