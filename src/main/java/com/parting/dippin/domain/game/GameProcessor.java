package com.parting.dippin.domain.game;

import com.parting.dippin.core.exception.CommonCodeAndMessage;
import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.api.game.dto.socket.GameProcessReqDto;
import com.parting.dippin.api.game.dto.socket.GameProcessResDto;
import com.parting.dippin.domain.game.exception.GameCodeAndMessage;
import com.parting.dippin.domain.game.exception.GameTypeException;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.domain.game.service.GameRecorderService;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

public class GameProcessor {

    private final int gameId;
    private final int memberId;

    private final String time;

    private final double curDistance;
    private final double curAvgSpeed;
    private final double curMaxSpeed;

    private final double curLatitude;
    private final double curLongitude;

    private GamePlayerEntity gamePlayer;
    private GameEntity game;

    private boolean isFinished = false;
    private int winnerId;

    public GameProcessor(int gameId, int memberId, GameProcessReqDto reqDto) {
        this.gameId = gameId;
        this.memberId = memberId;
        this.time = reqDto.getTime();
        this.curDistance = reqDto.getDistance();
        this.curAvgSpeed = reqDto.getAvgSpeed();
        this.curMaxSpeed = reqDto.getMaxSpeed();
        this.curLatitude = reqDto.getLatitude();
        this.curLongitude = reqDto.getLongitude();
    }

    public GameProcessResDto process(GameReader gameReader,
        GameRecorderService gameRecorderService) {
        game = gameReader.getGame(gameId);
        validateGame();

        gamePlayer = gameReader.getPlayer(gameId, memberId);
        validateProcess();

        recordData(gameRecorderService);

        boolean isTimeoutExceeded = isTimeoutExceeded();
        boolean isFullCompleted = isFullCompleted();

        if (isTimeoutExceeded || isFullCompleted) {
            finishGame(gameReader);
        }

        if (isFinished) {
            return new GameProcessResDto(gameId, memberId, time, curLatitude, curLongitude,
                curDistance, winnerId);
        }

        return new GameProcessResDto(gameId, memberId, time, curLatitude, curLongitude,
            curDistance);
    }

    /**
     * 현재 게임이 진행중인지 확인하기
     */
    private void validateGame() {
        if (game.getProgressStatus() != ProgressStatus.ONGOING) {
            throw GameTypeException.from(GameCodeAndMessage.NOT_AVAILABLE_GAME);
        }
    }

    /**
     * 진행 거리 확인하기 (줄어들었으면 에러 전송)
     */
    private void validateProcess() {
        if (curDistance < gamePlayer.getDistance()) {
            throw CommonException.from(CommonCodeAndMessage.BAD_REQUEST);
        }
    }

    /**
     * 시간 확인하고 제한 시간이 지났으면 더 멀리 간 유저 승리 처리
     */
    private boolean isTimeoutExceeded() {
        Duration limitDuration = Duration.ofMinutes(game.getTimeLimit());

        LocalTime inputTime = LocalTime.parse(time);

        Duration inputDuration = Duration.ofHours(inputTime.getHour())
            .plusMinutes(inputTime.getMinute())
            .plusSeconds(inputTime.getSecond());

        return !inputDuration.minus(limitDuration).isNegative();
    }

    /**
     * 게임 종료 처리
     */
    private void finishGame(GameReader gameReader) {
        List<GamePlayerEntity> gamePlayers = gameReader.getPlayers(gameId);

        gamePlayers.sort(Comparator.comparingDouble(GamePlayerEntity::getDistance).reversed());

        GamePlayerEntity winner = gamePlayers.get(0);
        List<GamePlayerEntity> losers = gamePlayers.subList(1, gamePlayers.size());


        winner.win(time);
        losers.forEach(loser -> loser.lose(time));

        winnerId = winner.getMemberId();
        isFinished = true;

        game.finish();
    }

    /**
     * 완주 여부 확인
     */
    private boolean isFullCompleted() {
        return game.getDistance() <= curDistance;
    }

    /**
     * 기록 저장
     */
    private void recordData(GameRecorderService service) {
        gamePlayer.process(curDistance, curAvgSpeed, curMaxSpeed);

        service.recordGeoData(gameId, memberId, time, curLatitude, curLongitude, curDistance);
    }
}
