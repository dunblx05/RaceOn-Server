package com.parting.dippin.api.game.service.impl;

import com.parting.dippin.domain.game.GameStarter;
import com.parting.dippin.api.game.dto.socket.GameStartResDto;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.domain.game.service.GameStatusChangerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Exception Game[NOT_GAME_MEMBER]
 * @Exception Game[ALREADY_PARTICIPANT_MEMBER]
 * @Exception Game[NOT_AVAILABLE_GAME]
 * @Exception Game[ALREADY_ONGOING_GAME]
 * @Exception Game[ALREADY_FINISHED_GAME]
 * @Exception Game[GAME_NOT_FOUND]
 */
@RequiredArgsConstructor
@Service
public class GameStarterService{

    private final GameReader gameReader;
    private final GameStatusChangerService gameStatusChangerService;

    @Transactional
    public GameStartResDto start(int gameId, int participantId) {
        GameStarter gameStarter = new GameStarter(gameId, participantId);

        return gameStarter.start(gameReader, gameStatusChangerService);
    }
}
