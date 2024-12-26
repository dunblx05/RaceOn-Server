package com.parting.dippin.domain.game.service.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.domain.game.dto.socket.GameStartResDto;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.PlayerStatus;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.repository.GamePlayerRepository;
import com.parting.dippin.entity.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameStarterService implements GameSocketService {

    private final GamePlayerRepository gamePlayerRepository;
    private final GameRepository gameRepository;

    @Transactional
    @Override
    public GameStartResDto invoke(int gameId, int memberId, String data)
        throws JsonProcessingException, NoSuchElementException {

        LocalDateTime startTime = null;

        // TODO - 이미 시작 했을 경우 데이터 처리 하지 못하도록 해야함

        boolean canStart = this.canStartGame(gameId, memberId);
        if (canStart) {
            startTime = this.startGame(gameId);
        }

        return new GameStartResDto(gameId, canStart, startTime);
    }

    /**
     * 게임 시작 가능 여부 체크 (먼저 들어온 유저는 참여로 변경만 하고 대기)
     */
    private boolean canStartGame(int gameId, int memberId) {
        List<GamePlayerEntity> gamePlayerEntities = this.gamePlayerRepository.findByGameId(gameId);
        boolean canStart = true;

        for (GamePlayerEntity gamePlayer : gamePlayerEntities) {
            if (memberId == gamePlayer.getMemberId()) {
                gamePlayer.participate();
                this.gamePlayerRepository.save(gamePlayer);
                continue;
            }

            if (gamePlayer.getPlayerStatus() != PlayerStatus.PARTICIPATION) {
                canStart = false;
            }
        }

        return canStart;
    }

    /**
     * 게임이 시작가능한 경우 약 15초 뒤에 게임이 시작되도록 한다.
     */
    private LocalDateTime startGame(int gameId) {
        GameEntity gameEntity = gameRepository.findById(gameId).orElseThrow();
        LocalDateTime startTime = gameEntity.start();
        this.gameRepository.save(gameEntity);

        return startTime;
    }
}
