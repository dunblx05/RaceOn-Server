package com.parting.dippin.domain.game.dto.socket;

import com.parting.dippin.core.base.BaseSocketData;
import lombok.Getter;

@Getter
public class GameProcessResDto implements BaseSocketData {
    int gameId;
    int memberId;
    String time;
    double latitude;
    double longitude;
    double distance;

    public GameProcessResDto(int gameId, int memberId, String time, double latitude,
        double longitude, double distance) {
        this.gameId = gameId;
        this.memberId = memberId;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }
}
