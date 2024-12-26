package com.parting.dippin.domain.game.dto.socket;

import com.parting.dippin.core.base.BaseSocketData;
import lombok.Getter;

@Getter
public class GameEndResDto implements BaseSocketData {

    int gameId;
    int winMemberId;

    public GameEndResDto(int gameId, int winMemberId) {
        this.gameId = gameId;
        this.winMemberId = winMemberId;
    }
}
