package com.parting.dippin.api.game.service.impl;

import com.parting.dippin.api.game.dto.socket.GameStopReqDto;
import com.parting.dippin.api.game.dto.socket.GameStopResDto;
import com.parting.dippin.domain.game.GameStop;
import com.parting.dippin.domain.game.service.GameReader;
import com.parting.dippin.domain.game.service.GameStatusChangerService;
import com.parting.dippin.domain.game.service.GameStatusReader;
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
public class GameStopService {

    private final GameReader gameReader;
    private final GameStatusChangerService gameStatusChangerService;
    private final GameStatusReader gameStatusReader;

    @Transactional
    public GameStopResDto stop(int gameId, int memberId, GameStopReqDto dto) {
        GameStop gameStop = new GameStop(gameId, dto.getRequestMemberId(), memberId);

        return gameStop.stopGame(
                gameReader,
                gameStatusChangerService,
                gameStatusReader,
                dto.isAgree()
        );
    }
}
