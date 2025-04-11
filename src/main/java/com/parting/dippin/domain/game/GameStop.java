package com.parting.dippin.domain.game;

import static com.parting.dippin.entity.game.enums.MemberGameStatus.INVITABLE;

import com.parting.dippin.api.game.dto.socket.GameStopResDto;
import com.parting.dippin.domain.game.exception.GameCodeAndMessage;
import com.parting.dippin.domain.game.exception.GameTypeException;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.domain.game.service.GameStatusChangerService;
import com.parting.dippin.domain.game.service.GameStatusReader;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.PlayerStatus;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import java.util.List;

public class GameStop {

    int gameId;
    int requestMemberId;
    int curMemberId;
    boolean isInProgress;
    boolean isAgree;

    GameEntity game;
    List<GamePlayerEntity> gamePlayers;

    public GameStop(int gameId, int requestMemberId, int curMemberId) {
        this.gameId = gameId;
        this.requestMemberId = requestMemberId;
        this.curMemberId = curMemberId;
        this.isInProgress = true;
        this.isAgree = false;
    }

    public GameStopResDto stopGame(
            GameReader gameReader,
            GameStatusChangerService gameStatusChangerService,
            GameStatusReader gameStatusReader,
            boolean isAgree
    ) {
        game = gameReader.getGame(gameId);
        validateGame();

        gamePlayers = gameReader.getPlayers(gameId);
        validateGamePlayers(gameStatusChangerService);

        // 요청자가 아닐 경우 동의 여부 확인 진행 및 동의 여부에 따른 게임 중단
        if (requestMemberId != curMemberId) {
            agree(isAgree);

            if (isAgree) {
                stopGame(gameReader, gameStatusReader);
            }
        }

        return new GameStopResDto(gameId, requestMemberId, curMemberId, isInProgress, this.isAgree);
    }

    /**
     * 중단 동의 여부 주입
     */
    private void agree(boolean isAgree) {
        this.isInProgress = false;
        this.isAgree = isAgree;
    }

    /**
     * 게임이 진행 중인지 확인
     */
    private void validateGame() {
        assert game != null;
        if (game.getProgressStatus() != ProgressStatus.ONGOING) {
            throw GameTypeException.from(GameCodeAndMessage.NOT_ONGOING_GAME);
        }
    }

    /**
     * 게임 참가자들의 상태를 확인
     */
    private void validateGamePlayers(GameStatusChangerService gameStatusChangerService) {
        assert !gamePlayers.isEmpty();

        int count = 0;

        for (GamePlayerEntity gamePlayer : gamePlayers) {
            if (gamePlayer.getPlayerStatus() != PlayerStatus.PARTICIPATION) {
                gameStatusChangerService.changeGameWithAsync(gameId, gamePlayer.getPlayerStatus());
                throw GameTypeException.from(GameCodeAndMessage.NOT_AVAILABLE_GAME);
            }

            if (curMemberId == gamePlayer.getMemberId()) {
                count++;
            }
        }

        if (count == 0) {
            throw GameTypeException.from(GameCodeAndMessage.NOT_GAME_MEMBER);
        }
    }

    /**
     * 중단 동의 했을 경우 게임 중단 진행
     */
    private void stopGame(
            GameReader gameReader,
            GameStatusReader gameStatusReader
    ) {
        game.stop();

        gamePlayers.forEach(player -> {
            String finishedTime = gameReader.getMaxTime(gameId, player.getMemberId());

            if (player.getMemberId() == requestMemberId) {
                player.stop(finishedTime);
            } else {
                player.win(finishedTime);
            }
        });

        gamePlayers.stream()
                .map(GamePlayerEntity::getMemberId)
                .map(gameStatusReader::findByMemberId)
                .forEach(status -> status.updateStatus(INVITABLE));
    }
}
