package com.parting.dippin.api.game.dto.socket;

import com.parting.dippin.core.base.BaseSocketData;
import lombok.Getter;

@Getter
public class GameInvitationRejectResDto implements BaseSocketData {
    private final int gameId;
    private final boolean failMatching;

    public GameInvitationRejectResDto(int gameId) {
        this.gameId = gameId;
        this.failMatching = true;
    }
}
