package com.parting.dippin.entity.game.player.repository;

public interface QGamePlayerRepository {

    boolean existsMatchingPlayer(int memberId, int friendId);
}
