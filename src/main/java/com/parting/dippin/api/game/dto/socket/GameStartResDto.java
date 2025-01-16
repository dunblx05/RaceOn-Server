package com.parting.dippin.api.game.dto.socket;

import com.parting.dippin.core.base.BaseSocketData;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GameStartResDto implements BaseSocketData {

    int gameId;
    boolean isMatched;
    LocalDateTime startTime;

    public GameStartResDto(int gameId, boolean isMatched, LocalDateTime startTime) {
        this.gameId = gameId;
        this.isMatched = isMatched;
        this.startTime = startTime;
    }
}
