package com.parting.dippin.entity.game.player.repository;

import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.GamePlayerId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlayerRepository extends JpaRepository<GamePlayerEntity, GamePlayerId> {

    GamePlayerEntity findByGameIdAndMemberId(Integer gameId, Integer memberId);

    List<GamePlayerEntity> findByGameId(Integer gameId);

    Integer game(GameEntity game);
}
