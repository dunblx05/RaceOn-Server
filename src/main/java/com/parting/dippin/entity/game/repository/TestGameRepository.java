package com.parting.dippin.entity.game.repository;

import com.parting.dippin.entity.game.GameEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestGameRepository extends JpaRepository<GameEntity, Integer> {

    List<GameEntity> findAll();
}
