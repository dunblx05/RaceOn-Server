package com.parting.dippin.api.game.dto.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameStopReqDto {
    int requestMemberId;
    boolean isAgree;
}
