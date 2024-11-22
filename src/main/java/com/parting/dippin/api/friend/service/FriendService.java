package com.parting.dippin.api.friend.service;

import com.parting.dippin.domain.friend.Friend;
import com.parting.dippin.domain.friend.service.FriendAddingService;
import com.parting.dippin.domain.friend.service.FriendValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendAddingService friendAddingService;
    private final FriendValidationService friendValidationService;

    public void addFriend(int memberId, int friendId) {
        Friend friend = new Friend(memberId, friendId);
        friend.addFriend(friendValidationService, friendAddingService);
    }
}
