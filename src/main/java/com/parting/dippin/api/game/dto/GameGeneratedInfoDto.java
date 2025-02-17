package com.parting.dippin.api.game.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GameGeneratedInfoDto {

    int gameId;

    int requestMemberId;
    String requestNickname;
    String requestProfileImageUrl;

    int receivedMemberId;
    String receivedNickname;
    String receivedProfileImageUrl;

    double distance;
    int timeLimit;

    @Builder
    private GameGeneratedInfoDto(
            int gameId,
            int requestMemberId,
            String requestNickname,
            String requestProfileImageUrl,
            int receivedMemberId,
            String receivedNickname,
            String receivedProfileImageUrl,
            double distance,
            int timeLimit
    ) {
        this.gameId = gameId;
        this.requestMemberId = requestMemberId;
        this.requestNickname = requestNickname;
        this.requestProfileImageUrl = requestProfileImageUrl;
        this.receivedMemberId = receivedMemberId;
        this.receivedNickname = receivedNickname;
        this.receivedProfileImageUrl = receivedProfileImageUrl;
        this.distance = distance;
        this.timeLimit = timeLimit;
    }
}
