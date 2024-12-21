package com.parting.dippin.entity.game.repository;

import com.parting.dippin.entity.game.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Integer>, QGameRepository {

}
