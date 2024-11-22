package com.parting.dippin.api.friend.service;

import com.parting.dippin.domain.friend.dao.IFriendDAO;
import com.parting.dippin.domain.friend.dto.FriendDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendReader {

    private final IFriendDAO friendDAO;

    public List<FriendDto> getFriends(int memberId) {
        return this.friendDAO.getFriends(memberId);
    }
}
