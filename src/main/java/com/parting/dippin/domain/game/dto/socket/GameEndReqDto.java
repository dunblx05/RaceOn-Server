package com.parting.dippin.domain.game.dto.socket;

import lombok.Getter;

@Getter
public class GameEndReqDto {

    String time;
    double latitude;
    double longitude;
    double distance;

    double avgSpeed;
    double maxSpeed;
}
