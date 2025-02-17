package com.parting.dippin.domain.game;

import com.parting.dippin.api.game.dto.GameGeneratedInfoDto;
import com.parting.dippin.api.game.dto.PostGameReqDto;
import com.parting.dippin.domain.friend.exception.FriendCodeAndMessage;
import com.parting.dippin.domain.friend.exception.FriendTypeException;
import com.parting.dippin.domain.friend.service.FriendValidationService;
import com.parting.dippin.domain.game.exception.GameCodeAndMessage;
import com.parting.dippin.domain.game.exception.GameTypeException;
import com.parting.dippin.domain.game.service.GameGeneratorService;
import com.parting.dippin.domain.game.service.GameValidationService;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.member.MemberEntity;

public class GameRegister {

    int memberId;
    int friendId;

    int timeLimit;
    double distance;

    public GameRegister(int memberId) {
        this.memberId = memberId;
    }

    public GameGeneratedInfoDto register(
        GameValidationService gameValidationService,
        FriendValidationService friendValidationService,
        MemberReader memberReader,
        GameGeneratorService gameGeneratorService,
        PostGameReqDto postGameReqDto
    ) {
        this.friendId = postGameReqDto.getFriendId();
        this.timeLimit = postGameReqDto.getTimeLimit();
        this.distance = postGameReqDto.getDistance();

        boolean isAlreadyMatching = this.isAlreadyMatching(gameValidationService);
        if (isAlreadyMatching) {
            throw GameTypeException.from(GameCodeAndMessage.ALREADY_MATCHING_OR_GAMING_MEMBER);
        }

        boolean isLinkedFriend = this.isLinkedFriend(friendValidationService);
        if (!isLinkedFriend) {
            throw FriendTypeException.from(FriendCodeAndMessage.NOT_FRIENDS_EXCEPTION);
        }

        int gameId = gameGeneratorService.generate(distance, timeLimit, memberId, friendId);

        MemberEntity requestMember = memberReader.getMemberById(memberId);
        MemberEntity receivedMember = memberReader.getMemberById(friendId);

        return GameGeneratedInfoDto.builder()
                .gameId(gameId)
                .requestMemberId(memberId)
                .requestNickname(requestMember.getNickname())
                .requestProfileImageUrl(requestMember.getProfileImageUrl())
                .receivedMemberId(friendId)
                .receivedNickname(receivedMember.getNickname())
                .receivedProfileImageUrl(receivedMember.getProfileImageUrl())
                .distance(distance)
                .timeLimit(timeLimit)
                .build();
    }

    /**
     * 이미 매칭중인 유저가 있는지 확인
     */
    private boolean isAlreadyMatching(GameValidationService service) {
        return service.isAlreadyMatching(memberId, friendId);
    }

    /**
     * 서로 친구인지 확인
     */
    private boolean isLinkedFriend(FriendValidationService service) {
        return service.isLinkedFriend(memberId, friendId);
    }
}
