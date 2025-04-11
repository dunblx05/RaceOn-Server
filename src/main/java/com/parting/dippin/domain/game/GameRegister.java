package com.parting.dippin.domain.game;

import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.ALREADY_MATCHING_OR_GAMING_MEMBER;
import static com.parting.dippin.entity.game.enums.MemberGameStatus.NOT_INVITABLE;

import com.parting.dippin.api.game.dto.GameGeneratedInfoDto;
import com.parting.dippin.api.game.dto.PostGameReqDto;
import com.parting.dippin.domain.friend.exception.FriendCodeAndMessage;
import com.parting.dippin.domain.friend.exception.FriendTypeException;
import com.parting.dippin.domain.friend.service.FriendValidationService;
import com.parting.dippin.domain.game.exception.GameTypeException;
import com.parting.dippin.domain.game.service.GameGeneratorService;
import com.parting.dippin.domain.game.service.GameStatusReader;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.game.MemberGameStatusEntity;
import com.parting.dippin.entity.member.MemberEntity;

public class GameRegister {

    int memberId;
    int friendId;

    int timeLimit;
    double distance;

    public GameRegister(
            int memberId,
            PostGameReqDto dto
    ) {
        this.memberId = memberId;
        this.friendId = dto.getFriendId();
        this.timeLimit = dto.getTimeLimit();
        this.distance = dto.getDistance();
    }

    public GameGeneratedInfoDto register(
            GameStatusReader gameStatusReader,
            FriendValidationService friendValidationService,
            MemberReader memberReader,
            GameGeneratorService gameGeneratorService
    ) {
        MemberGameStatusEntity memberGameStatus = gameStatusReader.findByMemberId(memberId);
        MemberGameStatusEntity friendGameStatus = gameStatusReader.findByMemberId(friendId);

        validateAlreadyMatching(memberGameStatus);
        validateAlreadyMatching(friendGameStatus);
        validateFriendShip(friendValidationService);

        MemberEntity requestMember = memberReader.getMemberById(memberId);
        MemberEntity receivedMember = memberReader.getMemberById(friendId);

        int gameId = gameGeneratorService.generate(distance, timeLimit, memberId, friendId);

        memberGameStatus.updateStatus(NOT_INVITABLE);
        friendGameStatus.updateStatus(NOT_INVITABLE);

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
    private void validateAlreadyMatching(MemberGameStatusEntity gameStatus) {
        if (!gameStatus.isInvitable()) {
            throw GameTypeException.from(ALREADY_MATCHING_OR_GAMING_MEMBER);
        }
    }

    /**
     * 서로 친구인지 확인
     */
    private void validateFriendShip(FriendValidationService service) {
        boolean isFriend = service.isLinkedFriend(memberId, friendId);

        if (!isFriend) {
            throw FriendTypeException.from(FriendCodeAndMessage.NOT_FRIENDS_EXCEPTION);
        }
    }
}
