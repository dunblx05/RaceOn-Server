package com.parting.dippin.api.friend.service;

import com.parting.dippin.api.connection.service.ConnectionReader;
import com.parting.dippin.domain.friend.dto.FriendDto;
import com.parting.dippin.entity.connection.ConnectionStatusEntity;
import com.parting.dippin.entity.friends.repository.FriendsRepository;
import com.parting.dippin.entity.member.MemberEntity;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendReader {

    private final FriendsRepository friendsRepository;
    private final ConnectionReader connectionReader;

    public List<FriendDto> getFriends(int memberId) {
        List<MemberEntity> friends = this.friendsRepository.findByMemberId(memberId);
        List<Integer> friendIds = friends.stream().map(MemberEntity::getMemberId).toList();

        Map<Integer, ConnectionStatusEntity> connectionMap = connectionReader.getConnectionMap(friendIds);

        return friends.stream()
                .map(convertToFriendDto(connectionMap))
                .toList();
    }

    private Function<MemberEntity, FriendDto> convertToFriendDto(
            Map<Integer, ConnectionStatusEntity> connectionMap
    ) {
        return friend -> {
            ConnectionStatusEntity connectionStatus = connectionMap.get(friend.getMemberId());

            return FriendDto.of(friend, connectionStatus);
        };
    }
}
