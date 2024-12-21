package com.parting.dippin.api.game.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostGameReqDto {

    int friendId;

    double distance;
    int timeLimit;
}
