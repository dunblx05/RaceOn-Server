package com.parting.dippin.api.game.service.impl;

import com.parting.dippin.domain.game.GameProcessor;
import com.parting.dippin.api.game.dto.socket.GameProcessReqDto;
import com.parting.dippin.api.game.dto.socket.GameProcessResDto;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.domain.game.service.GameRecorderService;
import com.parting.dippin.domain.game.service.GameStatusReader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Exception Common[BAD_REQUEST]
 * @Exception Game[NOT_AVAILABLE_GAME]
 * @Exception Game[NOT_GAME_MEMBER]
 * @Exception Game[GAME_NOT_FOUND]
 */
@RequiredArgsConstructor
@Service
public class GameProcessorService {

    private final GameReader gameReader;
    private final GameRecorderService gameRecorderService;
    private final GameStatusReader gameStatusReader;

    @Transactional
    public GameProcessResDto process(int gameId, int memberId, GameProcessReqDto dto) {
        GameProcessor gameProcessor = new GameProcessor(gameId, memberId, dto);

        return gameProcessor.process(gameReader, gameRecorderService, gameStatusReader);
    }
}
