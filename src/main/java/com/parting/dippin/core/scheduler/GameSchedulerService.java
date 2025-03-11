package com.parting.dippin.core.scheduler;

import static com.parting.dippin.entity.game.enums.ProgressStatus.MATCHING;
import static com.parting.dippin.entity.game.enums.ProgressStatus.ONGOING;

import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.repository.GamePlayerRepository;
import com.parting.dippin.entity.game.repository.GameRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameSchedulerService {

    private final GameRepository gameRepository;
    private final GamePlayerRepository gamePlayerRepository;

    public List<GameEntity> scanDoesNotStartGames() {
        return gameRepository.findTop100ByProgressStatus(MATCHING);
    }

    public List<GameEntity> scanDoesNotFinishGames() {
        return gameRepository.findTop100ByProgressStatus(ONGOING);
    }

    @Transactional()
    public void cancelGame(GameEntity game) {
        List<GamePlayerEntity> players = gamePlayerRepository.findAllByGameId(game.getGameId());

        game.cancelGame();
        players.forEach(GamePlayerEntity::reject);

        gameRepository.save(game);
        gamePlayerRepository.saveAll(players);
    }

    @Transactional()
    public void terminateGame(GameEntity game) {
        game.exceedTimeLimit();

        gameRepository.save(game);
    }
}
