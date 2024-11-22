package com.parting.dippin.domain.friend.service;

import com.parting.dippin.domain.friend.Friend;
import com.parting.dippin.entity.friends.FriendsEntity;
import com.parting.dippin.entity.friends.repository.FriendsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendAddingService {

    private final FriendsRepository friendsRepository;

    public void addFriend(Friend friend) {
        List<FriendsEntity> friendsEntities = FriendsEntity.from(friend);

        friendsRepository.saveAll(friendsEntities);
    }
}
