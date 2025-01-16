package com.parting.dippin.api.game.dto.socket;

import lombok.Getter;

@Getter
public class GameProcessReqDto {

    String time;
    double latitude;
    double longitude;
    double distance;

    double avgSpeed;
    double maxSpeed;
}
