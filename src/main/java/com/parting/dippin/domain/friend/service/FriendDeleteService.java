package com.parting.dippin.domain.friend.service;

import com.parting.dippin.entity.friends.repository.FriendsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendDeleteService {

    private final FriendsRepository friendsRepository;

    public void delete(int memberId, int friendId) {
        friendsRepository.deleteByMemberIdAndFriendId(memberId, friendId);
    }
}
