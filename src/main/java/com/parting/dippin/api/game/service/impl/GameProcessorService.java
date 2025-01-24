package com.parting.dippin.api.game.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.domain.game.GameProcessor;
import com.parting.dippin.api.game.dto.socket.GameProcessReqDto;
import com.parting.dippin.api.game.dto.socket.GameProcessResDto;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.domain.game.service.GameRecorderService;
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
public class GameProcessorService implements GameSocketService {

    private final ObjectMapper objectMapper;
    private final GameReader gameReader;
    private final GameRecorderService gameRecorderService;

    @Transactional
    @Override
    public GameProcessResDto invoke(int gameId, int memberId, String data) throws JsonProcessingException {
        GameProcessReqDto reqDto = this.objectMapper.readValue(data, GameProcessReqDto.class);

        GameProcessor gameProcessor = new GameProcessor(gameId, memberId, reqDto);

        return gameProcessor.process(gameReader, gameRecorderService);
    }
}
