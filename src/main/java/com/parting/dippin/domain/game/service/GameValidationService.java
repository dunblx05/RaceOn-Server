package com.parting.dippin.domain.game.service;

import com.parting.dippin.entity.game.player.repository.GamePlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameValidationService {

    private final GamePlayerRepository gamePlayerRepository;

    public boolean isAlreadyMatching(int memberId, int friendId) {
        return this.gamePlayerRepository.existsMatchingPlayer(memberId, friendId);
    }
}
