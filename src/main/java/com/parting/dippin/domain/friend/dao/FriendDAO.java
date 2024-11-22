package com.parting.dippin.domain.friend.dao;

import com.parting.dippin.domain.friend.dto.FriendDto;
import com.parting.dippin.entity.friends.repository.FriendsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FriendDAO implements IFriendDAO {

    private final FriendsRepository friendsRepository;

    @Override
    public List<FriendDto> getFriends(int memberId) {
        return this.friendsRepository.findByMemberId(memberId);
    }
}
