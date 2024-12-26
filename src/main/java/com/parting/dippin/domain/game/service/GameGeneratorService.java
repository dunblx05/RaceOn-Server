package com.parting.dippin.domain.game.service;

import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.PlayerStatus;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.repository.GamePlayerRepository;
import com.parting.dippin.entity.game.repository.GameRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameGeneratorService {

    private final GameRepository gameRepository;
    private final GamePlayerRepository gamePlayerRepository;

    public int generate(double distance, int timeLimit, int requestMemberId, int receivedMemberId) {
        GameEntity game = GameEntity
            .generate(distance, timeLimit);

        int gameId = this.gameRepository.save(game).getGameId();

        List<GamePlayerEntity> gamePlayerEntities = new ArrayList<>();

        gamePlayerEntities.add(GamePlayerEntity.from(gameId, receivedMemberId, PlayerStatus.WAITING_TO_PARTICIPATE));
        gamePlayerEntities.add(GamePlayerEntity.from(gameId, requestMemberId, PlayerStatus.WAITING_TO_PARTICIPATE));

        this.gamePlayerRepository.saveAll(gamePlayerEntities);

        return gameId;
    }
}
