package com.parting.dippin.api.game;

import com.parting.dippin.api.game.handler.GameSocketHandler;
import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.core.base.BaseSocketData;
import com.parting.dippin.core.base.BaseSocketRequest;
import com.parting.dippin.core.base.BaseSocketResponse;
import com.parting.dippin.core.exception.CommonCodeAndMessage;
import com.parting.dippin.core.exception.CommonException;
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

    private final GameSocketHandler gameSocketHandler;

    @MessageMapping("/games/{gameId}/gamer/{memberId}")
    @SendTo("/topic/games/{gameId}")
    public BaseSocketResponse<BaseSocketData> process(
            @DestinationVariable int gameId,
            @DestinationVariable int memberId,
            BaseSocketRequest message
    ) {
        try {
            GameSocketService gameSocketService = this.gameSocketHandler.handle(message.getCommand());

            if (gameSocketService == null) {
                throw CommonException.from(CommonCodeAndMessage.BAD_REQUEST);
            }

            BaseSocketData data = gameSocketService.invoke(gameId, memberId, message.getData());

            return BaseSocketResponse.ok(message.getCommand(), data);
        } catch (CommonException e) {
            return BaseSocketResponse.error(e.getCodeAndMessage(), message.getCommand());
        } catch (Exception e) {
            return BaseSocketResponse.error(CommonCodeAndMessage.INTERNAL_SERVER_ERROR, message.getCommand());
        }
    }
}
