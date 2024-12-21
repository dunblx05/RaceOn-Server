package com.parting.dippin.api.game.service;

import com.parting.dippin.api.game.dto.GameGeneratedInfoDto;
import com.parting.dippin.api.game.dto.PostGameReqDto;
import com.parting.dippin.api.game.exception.UnlinkedFriendException;
import com.parting.dippin.domain.friend.service.FriendValidationService;
import com.parting.dippin.domain.game.service.GameGeneratorService;
import com.parting.dippin.domain.member.service.MemberReader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameService {

    private final MemberReader memberReader;
    private final FriendValidationService friendValidationService;
    private final GameGeneratorService gameGeneratorService;

    @Transactional
    public GameGeneratedInfoDto requestGame(int memberId, PostGameReqDto postGameReqDto) {

        int friendId = postGameReqDto.getFriendId();
        int timeLimit = postGameReqDto.getTimeLimit();
        double distance = postGameReqDto.getDistance();

        boolean isLinked = friendValidationService.isLinkedFriend(memberId, friendId);

        if (!isLinked) {
            throw new UnlinkedFriendException();
        }

        int gameId = gameGeneratorService.generate(distance, timeLimit, memberId, friendId);

        String requestNickname = this.memberReader.getNickname(memberId);
        String receivedNickname = this.memberReader.getNickname(friendId);

        return new GameGeneratedInfoDto(
            gameId,
            memberId, requestNickname,
            friendId, receivedNickname,
            distance, timeLimit
        );
    }
}
