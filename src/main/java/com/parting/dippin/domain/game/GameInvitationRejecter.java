package com.parting.dippin.domain.game;

import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.NOT_MATCHING_GAME;

import com.parting.dippin.api.game.dto.socket.GameInvitationRejectResDto;
import com.parting.dippin.core.base.BaseSocketData;
import com.parting.dippin.domain.game.exception.GameTypeException;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.player.GamePlayerEntity;

public class GameInvitationRejecter {

    private final int gameId;
    private final int myMemberId;

    public GameInvitationRejecter(int gameId, int myMemberId) {
        this.gameId = gameId;
        this.myMemberId = myMemberId;
    }

    public BaseSocketData reject(GameReader gameReader) {
        GameEntity game = gameReader.getGame(gameId);
        if(game.isNotMatching()){
            throw GameTypeException.from(NOT_MATCHING_GAME);
        }

        GamePlayerEntity gamePlayer = gameReader.getPlayer(gameId, myMemberId);
        gamePlayer.reject();
        game.failMatching();

        return new GameInvitationRejectResDto(gameId);
    }
}
