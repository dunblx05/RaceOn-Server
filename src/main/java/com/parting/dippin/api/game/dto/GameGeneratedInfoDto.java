package com.parting.dippin.api.game.dto;

import lombok.Getter;

@Getter
public class GameGeneratedInfoDto {

    int gameId;

    int requestMemberId;
    String requestNickname;

    int receivedMemberId;
    String receivedNickname;

    double distance;
    int timeLimit;

    public GameGeneratedInfoDto(
        int gameId,
        int requestMemberId, String requestNickname,
        int receivedMemberId, String receivedNickname,
        double distance, int timeLimit
    ) {
        this.gameId = gameId;

        this.requestMemberId = requestMemberId;
        this.requestNickname = requestNickname;

        this.receivedMemberId = receivedMemberId;
        this.receivedNickname = receivedNickname;

        this.distance = distance;
        this.timeLimit = timeLimit;
    }
}
