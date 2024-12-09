package com.parting.dippin.api.friend.service;

import com.parting.dippin.domain.friend.dto.FriendDto;
import com.parting.dippin.entity.friends.repository.FriendsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendReader {

    private final FriendsRepository friendsRepository;

    public List<FriendDto> getFriends(int memberId) {
        return this.friendsRepository.findByMemberId(memberId);
    }
}
