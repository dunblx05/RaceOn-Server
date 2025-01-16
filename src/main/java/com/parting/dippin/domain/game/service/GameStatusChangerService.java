package com.parting.dippin.domain.game.service;

import com.parting.dippin.entity.game.enums.PlayerStatus;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameStatusChangerService {

    private final GameRepository gameRepository;

    public void rejectGame(int gameId) {
        this.changeGameStatus(gameId, ProgressStatus.FAILED_MATCHING);
    }

    @Async
    @Transactional
    public void rejectGameWithAsync(int gameId) {
        this.changeGameStatus(gameId, ProgressStatus.FAILED_MATCHING);
    }

    public void changeGame(int gameId, PlayerStatus playerStatus) {
        if (playerStatus == PlayerStatus.WAITING_TO_PARTICIPATE) {
            this.changeGameStatus(gameId, ProgressStatus.MATCHING);
        } else if (playerStatus == PlayerStatus.REJECT) {
            this.changeGameStatus(gameId, ProgressStatus.FAILED_MATCHING);
        }
    }

    @Async
    @Transactional
    public void changeGameWithAsync(int gameId, PlayerStatus playerStatus) {
        if (playerStatus == PlayerStatus.WAITING_TO_PARTICIPATE) {
            this.changeGameStatus(gameId, ProgressStatus.MATCHING);
        } else if (playerStatus == PlayerStatus.REJECT) {
            this.changeGameStatus(gameId, ProgressStatus.FAILED_MATCHING);
        }
    }

    private void changeGameStatus(int gameId, ProgressStatus progressStatus) {
        this.gameRepository.changeGameStatus(gameId, progressStatus);
    }
}
