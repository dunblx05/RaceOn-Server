package com.parting.dippin.api.game;

import com.parting.dippin.api.game.dto.socket.GameProcessReqDto;
import com.parting.dippin.api.game.dto.socket.GameStopReqDto;
import com.parting.dippin.api.game.service.impl.GameInvitationRejectService;
import com.parting.dippin.api.game.service.impl.GameProcessorService;
import com.parting.dippin.api.game.service.impl.GameStarterService;
import com.parting.dippin.api.game.service.impl.GameStopService;
import com.parting.dippin.core.base.BaseSocketData;
import com.parting.dippin.core.base.BaseSocketRequest;
import com.parting.dippin.core.base.BaseSocketResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class GameSocketController {

    private final GameProcessorService gameProcessorService;
    private final GameStarterService gameStarterService;
    private final GameStopService gameStopService;
    private final GameInvitationRejectService gameInvitationRejectService;

    @MessageMapping("/games/{gameId}/gamer/{memberId}/start")
    @SendTo("/topic/games/{gameId}")
    public BaseSocketResponse<BaseSocketData> start(
            @DestinationVariable int gameId,
            @DestinationVariable int memberId,
            BaseSocketRequest<Void> message
    ) {
        BaseSocketData data = gameStarterService.start(gameId, memberId);

        return BaseSocketResponse.ok(message.getCommand(), data);
    }

    @MessageMapping("/games/{gameId}/gamer/{memberId}/process")
    @SendTo("/topic/games/{gameId}")
    public BaseSocketResponse<BaseSocketData> process(
            @DestinationVariable int gameId,
            @DestinationVariable int memberId,
            BaseSocketRequest<GameProcessReqDto> message
    ) {
        BaseSocketData data = gameProcessorService.process(gameId, memberId, message.getData());

        return BaseSocketResponse.ok(message.getCommand(), data);
    }

    @MessageMapping("/games/{gameId}/gamer/{memberId}/reject")
    @SendTo("/topic/games/{gameId}")
    public BaseSocketResponse<BaseSocketData> reject(
            @DestinationVariable int gameId,
            @DestinationVariable int memberId,
            BaseSocketRequest<Void> message
    ) {
        BaseSocketData data = gameInvitationRejectService.reject(gameId, memberId);

        return BaseSocketResponse.ok(message.getCommand(), data);
    }

    @MessageMapping("/games/{gameId}/gamer/{memberId}/stop")
    @SendTo("/topic/games/{gameId}")
    public BaseSocketResponse<BaseSocketData> stop(
            @DestinationVariable int gameId,
            @DestinationVariable int memberId,
            BaseSocketRequest<GameStopReqDto> message
    ) {
        BaseSocketData data = gameStopService.stop(gameId, memberId, message.getData());

        return BaseSocketResponse.ok(message.getCommand(), data);
    }
}
