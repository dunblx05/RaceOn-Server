package com.parting.dippin.api.game.dto.socket;

import lombok.Getter;

@Getter
public class GameStopReqDto {

    int requestMemberId;
    boolean isAgree;
}
