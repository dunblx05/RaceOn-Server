package com.parting.dippin.entity.friends.repository;

import com.parting.dippin.domain.friend.dto.FriendDto;
import java.util.List;

public interface QFriendsRepository {

    public List<FriendDto> findByMemberId(Integer memberId);
}
