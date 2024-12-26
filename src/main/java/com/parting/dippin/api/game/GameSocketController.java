package com.parting.dippin.api.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parting.dippin.api.game.handler.GameSocketHandler;
import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.core.base.BaseSocketData;
import com.parting.dippin.core.base.BaseSocketRequest;
import com.parting.dippin.core.base.BaseSocketResponse;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class GameSocketController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final GameSocketHandler gameSocketHandler;

    @MessageMapping("/games/{gameId}/gamer/{memberId}")
    @SendTo("/topic/games/{gameId}")
    public BaseSocketResponse<BaseSocketData> sample(@DestinationVariable int gameId,
        @DestinationVariable int memberId,
        BaseSocketRequest message) {

        try {
            GameSocketService gameSocketService = this.gameSocketHandler.handle(
                message.getCommand());

            if (gameSocketService == null) {
                return BaseSocketResponse.failure(message.getCommand());
            }

            BaseSocketData data = gameSocketService.invoke(gameId, memberId, message.getData());

            return BaseSocketResponse.success(message.getCommand(), data);
        } catch (JsonProcessingException e) {
            return BaseSocketResponse.failure(message.getCommand());
        } catch (NoSuchElementException e) {
            return BaseSocketResponse.notFound(message.getCommand());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseSocketResponse.baseRequest(message.getCommand());
        }
    }
}
