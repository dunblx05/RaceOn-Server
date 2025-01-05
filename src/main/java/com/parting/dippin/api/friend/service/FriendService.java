package com.parting.dippin.api.friend.service;

import com.parting.dippin.domain.friend.FriendAdder;
import com.parting.dippin.domain.friend.FriendRemover;
import com.parting.dippin.domain.friend.service.FriendAddingService;
import com.parting.dippin.domain.friend.service.FriendDeleteService;
import com.parting.dippin.domain.friend.service.FriendValidationService;
import com.parting.dippin.domain.member.service.MemberCodeCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final MemberCodeCheckerService memberCodeCheckerService;
    private final FriendAddingService friendAddingService;
    private final FriendValidationService friendValidationService;
    private final FriendDeleteService friendDeleteService;

    public void addFriend(int memberId, String friendCode) {
        int friendId = this.memberCodeCheckerService.invoke(friendCode);

        FriendAdder friendAdder = new FriendAdder(memberId, friendId);
        friendAdder.addFriend(friendValidationService, friendAddingService);
    }

    @Transactional
    public void deleteFriend(int memberId, int friendId) {
        FriendRemover friendRemover = new FriendRemover(memberId, friendId);

        friendRemover.delete(friendDeleteService, friendValidationService);
    }
}
