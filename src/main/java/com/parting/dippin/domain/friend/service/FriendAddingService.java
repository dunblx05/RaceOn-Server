package com.parting.dippin.domain.friend.service;

import com.parting.dippin.domain.friend.FriendAdder;
import com.parting.dippin.entity.friends.FriendsEntity;
import com.parting.dippin.entity.friends.repository.FriendsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendAddingService {

    private final FriendsRepository friendsRepository;

    public void addFriend(FriendAdder friendAdder) {
        List<FriendsEntity> friendsEntities = FriendsEntity.from(friendAdder);

        friendsRepository.saveAll(friendsEntities);
    }
}
