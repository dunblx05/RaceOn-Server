package com.parting.dippin.domain.friend.dao;

import com.parting.dippin.domain.friend.dto.FriendDto;
import java.util.List;

public interface IFriendDAO {

    public List<FriendDto> getFriendList(Integer memberId);
}
