package com.parting.dippin.api.game.handler;

import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.domain.game.service.socket.GameEndService;
import com.parting.dippin.domain.game.service.socket.GameProcessorService;
import com.parting.dippin.domain.game.service.socket.GameStarterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GameSocketHandler {

    private final GameProcessorService gameProcessorService;
    private final GameStarterService gameStarterService;
    private final GameEndService gameEndService;

    public GameSocketService handle(String command) {
        return switch (command) {
            case "START" -> this.gameStarterService;
            case "PROCESS" -> this.gameProcessorService;
            case "END" -> this.gameEndService;
            // TODO - 게임 중단 요청
            default -> null;
        };
    }
}
