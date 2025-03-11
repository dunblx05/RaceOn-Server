package com.parting.dippin.core.scheduler;

import static com.parting.dippin.entity.game.enums.ProgressStatus.MATCHING;
import static com.parting.dippin.entity.game.enums.ProgressStatus.ONGOING;

import com.parting.dippin.entity.game.GameEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameTerminatorScheduler {

    public static final int LIMIT_OF_LOOP = 100;
    public static final int GAME_CANCEL_DEAD_LINE = 40;
    private final GameSchedulerService gameSchedulerService;

    private static final int THREE_MINUTE = 1000 * 60 * 3;

    @Scheduled(fixedDelay = THREE_MINUTE)
    public void cancelDoesNotStartGames() {
        for (int i = 0; i < LIMIT_OF_LOOP; i++) {
            List<GameEntity> games = gameSchedulerService.scanDoesNotStartGames();
            if (games.isEmpty()) {
                break;
            }

            LocalDateTime threshold = LocalDateTime.now().minusSeconds(GAME_CANCEL_DEAD_LINE);

            games.stream()
                    .filter(isGameStartTimeExceeded(threshold))
                    .forEach(gameSchedulerService::cancelGame);
        }
    }

    @NotNull
    private Predicate<GameEntity> isGameStartTimeExceeded(LocalDateTime threshold) {
        return game -> game.getCreatedAt().isBefore(threshold) && game.getProgressStatus() == MATCHING;
    }

    @Scheduled(fixedDelay = THREE_MINUTE)
    public void terminateExceedTimeLimitGames() {
        for (int i = 0; i < LIMIT_OF_LOOP; i++) {
            List<GameEntity> games = gameSchedulerService.scanDoesNotFinishGames();
            if (games.isEmpty()) {
                break;
            }

            LocalDateTime now = LocalDateTime.now();

            games.stream()
                    .filter(isGameFinishTimeLimitExceeded(now))
                    .forEach(gameSchedulerService::terminateGame);
        }
    }

    @NotNull
    private Predicate<GameEntity> isGameFinishTimeLimitExceeded(LocalDateTime now) {
        return game -> game.isExceedTimeLimit(now) && game.getProgressStatus() == ONGOING;
    }
}
