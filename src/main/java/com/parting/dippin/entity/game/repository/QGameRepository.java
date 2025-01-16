package com.parting.dippin.entity.game.repository;

import com.parting.dippin.entity.game.enums.ProgressStatus;

public interface QGameRepository {

    void changeGameStatus(int gameId, ProgressStatus progressStatus);
}
