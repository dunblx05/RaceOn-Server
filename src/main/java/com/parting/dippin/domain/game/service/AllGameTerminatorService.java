package com.parting.dippin.domain.game.service;

import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.repository.TestGameRepository;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"dev", "test"})
@Service
@RequiredArgsConstructor
public class AllGameTerminatorService {

    private final TestGameRepository testGameRepository;
    private final GameReader gameReader;

    public void terminateAll() {
        List<GameEntity> games = testGameRepository.findAll().stream()
                .filter(gameEntity -> !gameEntity.getProgressStatus().equals(ProgressStatus.FINISHED))
                .toList();

        games.forEach(GameEntity::finish);

        games.stream()
                .map(gameEntity -> gameReader.getPlayers(gameEntity.getGameId()))
                .forEach(assignPlayerResults());
    }

    @NotNull
    private static Consumer<List<GamePlayerEntity>> assignPlayerResults() {
        return gamePlayerEntities -> {
            assert gamePlayerEntities.size() == 2;

            GamePlayerEntity winner = gamePlayerEntities.get(0);
            GamePlayerEntity loser = gamePlayerEntities.get(1);

            winner.win("30:00");
            loser.lose("30:00");
        };
    }
}
