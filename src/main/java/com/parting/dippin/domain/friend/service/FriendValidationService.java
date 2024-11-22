package com.parting.dippin.domain.friend.service;

import com.parting.dippin.entity.friends.repository.FriendsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendValidationService {

    private final FriendsRepository friendsRepository;

    public boolean isAlreadyFriend(int memberId, int friendId) {
        return friendsRepository.existsFriendsByMyMemberIdAndMemberId(memberId, friendId);
    }
}
