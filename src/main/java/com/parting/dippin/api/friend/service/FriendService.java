package com.parting.dippin.api.friend.service;

import com.parting.dippin.domain.friend.FriendAdder;
import com.parting.dippin.domain.friend.service.FriendAddingService;
import com.parting.dippin.domain.friend.service.FriendValidationService;
import com.parting.dippin.domain.member.service.MemberCodeCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final MemberCodeCheckerService memberCodeCheckerService;
    private final FriendAddingService friendAddingService;
    private final FriendValidationService friendValidationService;

    public void addFriend(int memberId, String friendCode) {
        int friendId = this.memberCodeCheckerService.invoke(friendCode);

        FriendAdder friendAdder = new FriendAdder(memberId, friendId);
        friendAdder.addFriend(friendValidationService, friendAddingService);
    }
}
