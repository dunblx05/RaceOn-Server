package com.parting.dippin.domain.game;

import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.NOT_MATCHING_GAME;
import static com.parting.dippin.entity.game.enums.MemberGameStatus.INVITABLE;

import com.parting.dippin.api.game.dto.socket.GameInvitationRejectResDto;
import com.parting.dippin.core.base.BaseSocketData;
import com.parting.dippin.domain.game.exception.GameCodeAndMessage;
import com.parting.dippin.domain.game.exception.GameTypeException;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.domain.game.service.GameStatusReader;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import java.util.List;

public class GameInvitationRejecter {

    private final int gameId;
    private final int myMemberId;

    public GameInvitationRejecter(int gameId, int myMemberId) {
        this.gameId = gameId;
        this.myMemberId = myMemberId;
    }

    public BaseSocketData reject(
            GameReader gameReader,
            GameStatusReader gameStatusReader
    ) {
        GameEntity game = gameReader.getGame(gameId);
        if (game.isNotMatching()) {
            throw GameTypeException.from(NOT_MATCHING_GAME);
        }

        game.failMatching();

        List<GamePlayerEntity> gamePlayerEntities = game.getGamePlayerEntities();
        rejectGame(gamePlayerEntities);

        gamePlayerEntities.stream()
                .map(GamePlayerEntity::getMemberId)
                .map(gameStatusReader::findByMemberId)
                .forEach(status -> status.updateStatus(INVITABLE));

        return new GameInvitationRejectResDto(gameId);
    }

    private void rejectGame(List<GamePlayerEntity> gamePlayerEntities) {
        GamePlayerEntity myEntity = gamePlayerEntities.stream()
                .filter(player -> player.getMemberId() == myMemberId)
                .findAny()
                .orElseThrow(() -> GameTypeException.from(GameCodeAndMessage.NOT_GAME_MEMBER));

        myEntity.reject();
    }
}
