package com.parting.dippin.api.game.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.api.game.dto.socket.GameStopReqDto;
import com.parting.dippin.api.game.dto.socket.GameStopResDto;
import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.domain.game.GameStop;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.domain.game.service.GameStatusChangerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Exception Game[NOT_ONGOING_GAME]
 * @Exception Game[NOT_AVAILABLE_GAME]
 * @Exception Game[NOT_GAME_MEMBER]
 * @Exception Game[GAME_NOT_FOUND]
 */
@RequiredArgsConstructor
@Service
public class GameStopService implements GameSocketService {

    private final ObjectMapper objectMapper;
    private final GameReader gameReader;
    private final GameStatusChangerService gameStatusChangerService;

    @Transactional
    @Override
    public GameStopResDto invoke(int gameId, int memberId, String data)
        throws JsonProcessingException {

        GameStopReqDto reqDto = this.objectMapper.readValue(data, GameStopReqDto.class);

        GameStop gameStop = new GameStop(gameId, reqDto.getRequestMemberId(), memberId);

        return gameStop.stopGame(gameReader, gameStatusChangerService, reqDto.isAgree());
    }
}
