package com.parting.dippin.entity.game.repository;

import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Integer>, QGameRepository {

    Optional<GameEntity> findGameEntityByGameIdAndProgressStatus(Integer gameId, ProgressStatus progressStatus);
}
