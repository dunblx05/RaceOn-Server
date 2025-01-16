package com.parting.dippin.entity.game.player.repository;

import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.GamePlayerId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlayerRepository extends JpaRepository<GamePlayerEntity, GamePlayerId>,
    QGamePlayerRepository {

    Optional<GamePlayerEntity> findByGameIdAndMemberId(Integer gameId, Integer memberId);

    List<GamePlayerEntity> findAllByGameId(Integer gameId);
}
