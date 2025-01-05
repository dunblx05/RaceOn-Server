package com.parting.dippin.domain.friend;

import static com.parting.dippin.domain.friend.exception.FriendCodeAndMessage.NOT_FRIENDS_EXCEPTION;

import com.parting.dippin.domain.friend.exception.FriendTypeException;
import com.parting.dippin.domain.friend.service.FriendDeleteService;
import com.parting.dippin.domain.friend.service.FriendValidationService;

public class FriendRemover {

    private final int memberId;
    private final int friendId;

    public FriendRemover(int memberId, int friendId) {
        this.memberId = memberId;
        this.friendId = friendId;
    }

    public void delete(
            FriendDeleteService friendDeleteService,
            FriendValidationService friendValidationService
    ) {
        boolean isFriend = friendValidationService.isLinkedFriend(memberId, friendId);

        if (!isFriend) {
            throw FriendTypeException.from(NOT_FRIENDS_EXCEPTION);
        }

        friendDeleteService.delete(memberId, friendId);
    }
}
