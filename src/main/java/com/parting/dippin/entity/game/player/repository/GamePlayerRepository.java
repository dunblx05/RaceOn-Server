package com.parting.dippin.entity.game.player.repository;

import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.GamePlayerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlayerRepository  extends JpaRepository<GamePlayerEntity, GamePlayerId> {

}
