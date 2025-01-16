package com.parting.dippin.api.game.handler;

import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.api.game.service.impl.GameProcessorService;
import com.parting.dippin.api.game.service.impl.GameStarterService;
import com.parting.dippin.api.game.service.impl.GameStopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GameSocketHandler {

    private final GameProcessorService gameProcessorService;
    private final GameStarterService gameStarterService;
    private final GameStopService gameStopService;

    public GameSocketService handle(String command) {
        return switch (command) {
            case "START" -> this.gameStarterService;
            case "PROCESS" -> this.gameProcessorService;
            case "STOP" -> this.gameStopService;
            default -> null;
        };
    }
}
