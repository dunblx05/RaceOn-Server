package com.parting.dippin.api.game.dto.socket;

import com.parting.dippin.core.base.BaseSocketData;

public class GameStopResDto implements BaseSocketData {

    int gameId;
    int requestMemberId;
    int curMemberId;
    boolean isInProgress;
    boolean isAgree;

    public GameStopResDto(int gameId, int requestMemberId, int curMemberId, boolean isInProgress, boolean isAgree) {
        this.gameId = gameId;
        this.requestMemberId = requestMemberId;
        this.curMemberId = curMemberId;
        this.isInProgress = isInProgress;
        this.isAgree = isAgree;
    }
}
