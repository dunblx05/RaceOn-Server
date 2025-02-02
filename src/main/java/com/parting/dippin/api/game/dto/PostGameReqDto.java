package com.parting.dippin.api.game.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostGameReqDto {

    int friendId;
    double distance;
    int timeLimit;

    @Builder
    private PostGameReqDto(int friendId, double distance, int timeLimit) {
        this.friendId = friendId;
        this.distance = distance;
        this.timeLimit = timeLimit;
    }
}
