package com.parting.dippin.entity.game.player.repository;

import com.parting.dippin.entity.game.player.GamePlayerEntity;
import java.util.List;
import java.util.Optional;

public interface QGamePlayerRepository {

    boolean existsMatchingPlayer(int memberId, int friendId);
}
