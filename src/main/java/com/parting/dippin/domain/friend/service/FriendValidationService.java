package com.parting.dippin.domain.friend.service;

import com.parting.dippin.entity.friends.repository.FriendsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendValidationService {

    private final FriendsRepository friendsRepository;

    public boolean isLinkedFriend(int memberId, int friendId) {
        return friendsRepository.existsFriendsByMyMemberIdAndMemberId(memberId, friendId);
    }

    public boolean isSameUser(int memberId, int friendId) {
        return memberId == friendId;
    }
}
