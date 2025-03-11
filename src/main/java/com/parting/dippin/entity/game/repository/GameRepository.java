package com.parting.dippin.entity.game.repository;

import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Integer>, QGameRepository {
    List<GameEntity> findTop100ByProgressStatus(ProgressStatus matching);
}
