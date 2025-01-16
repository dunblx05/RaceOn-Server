package com.parting.dippin.domain.game;

import com.parting.dippin.api.game.dto.socket.GameStartResDto;
import com.parting.dippin.domain.game.exception.GameCodeAndMessage;
import com.parting.dippin.domain.game.exception.GameTypeException;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.domain.game.service.GameStatusChangerService;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.PlayerStatus;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import java.time.LocalDateTime;
import java.util.List;

public class GameStarter {

    private LocalDateTime startTime = null;
    private final int gameId;
    private final int participantId;
    private GamePlayerEntity participant;
    private List<GamePlayerEntity> players;
    private GameEntity game;

    public GameStarter(int gameId, int participantId) {
        this.gameId = gameId;
        this.participantId = participantId;
    }

    public GameStartResDto start(GameReader gameReader,
        GameStatusChangerService gameStatusChangerService) {
        game = gameReader.getGame(gameId);
        players = gameReader.getPlayers(gameId);

        validGameStatus();

        validParticipant(gameStatusChangerService);

        participant.participate();

        boolean isAllPlayersReady = isAllPlayersReady(gameStatusChangerService);

        if (isAllPlayersReady) {
            startTime = game.start();
        }

        return new GameStartResDto(gameId, isAllPlayersReady, startTime);
    }

    /**
     * 참여자가 게임에 참여 가능한 상태인지 확인
     */
    private void validParticipant(GameStatusChangerService gameStatusChangerService) {
        participant = players.stream()
            .filter(player -> player.getMemberId() == participantId)
            .findAny()
            .orElseThrow(() -> GameTypeException.from(GameCodeAndMessage.NOT_GAME_MEMBER));

        if (participant.getPlayerStatus() == PlayerStatus.PARTICIPATION) {
            throw GameTypeException.from(GameCodeAndMessage.ALREADY_PARTICIPANT_MEMBER);
        }

        if (participant.getPlayerStatus() == PlayerStatus.REJECT) {
            gameStatusChangerService.rejectGameWithAsync(gameId);
            throw GameTypeException.from(GameCodeAndMessage.NOT_AVAILABLE_GAME);
        }
    }

    /**
     * 참여자를 제외한 대기 인원이 있는지 확인 거절한 멤버가 있을 경우 게임 상태 변경 처리
     */
    private boolean isAllPlayersReady(GameStatusChangerService gameStatusChangerService) {
        List<GamePlayerEntity> waitingOtherPlayers = players.stream()
            .filter(player -> player.getMemberId() != participantId
                && player.getPlayerStatus() != PlayerStatus.PARTICIPATION)
            .toList();

        if (waitingOtherPlayers.isEmpty()) {
            return true;
        }

        boolean isRejectedGame = waitingOtherPlayers.stream()
            .anyMatch(player -> player.getPlayerStatus() == PlayerStatus.REJECT);

        if (isRejectedGame) {
            gameStatusChangerService.rejectGameWithAsync(gameId);
            throw GameTypeException.from(GameCodeAndMessage.NOT_AVAILABLE_GAME);
        }

        return false;
    }

    /**
     * 현재 게임이 참여 가능한 상태인지 확인
     */
    private void validGameStatus() {

        switch (game.getProgressStatus()) {
            case ONGOING:
                throw GameTypeException.from(GameCodeAndMessage.ALREADY_ONGOING_GAME);
            case FINISHED:
                throw GameTypeException.from(GameCodeAndMessage.ALREADY_FINISHED_GAME);
            case MATCHING:
                return;
            default:
                throw GameTypeException.from(GameCodeAndMessage.NOT_AVAILABLE_GAME);
        }
    }
}
