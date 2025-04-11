package com.parting.dippin.domain.game.service;

import static com.parting.dippin.entity.game.enums.MemberGameStatus.INVITABLE;

import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.repository.TestGameRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"dev", "test"})
@Service
@RequiredArgsConstructor
public class AllGameTerminatorService {

    private final TestGameRepository testGameRepository;
    private final GameStatusReader gameStatusReader;

    public void terminateAll() {
        List<GameEntity> games = testGameRepository.findAll().stream()
                .filter(gameEntity -> !gameEntity.getProgressStatus().equals(ProgressStatus.FINISHED))
                .toList();

        games.forEach(this::terminateGame);
    }

    private void terminateGame(GameEntity game) {
        game.finish();
        List<GamePlayerEntity> players = game.getGamePlayerEntities();

        assignPlayerResults(players);
        players.stream().mapToInt(GamePlayerEntity::getMemberId)
                .mapToObj(gameStatusReader::findByMemberId)
                .forEach(member -> member.updateStatus(INVITABLE));
    }

    private void assignPlayerResults(List<GamePlayerEntity> gamePlayerEntities) {
        assert gamePlayerEntities.size() == 2;

        GamePlayerEntity winner = gamePlayerEntities.get(0);
        GamePlayerEntity loser = gamePlayerEntities.get(1);

        winner.win("30:00");
        loser.lose("30:00");
    }
}
