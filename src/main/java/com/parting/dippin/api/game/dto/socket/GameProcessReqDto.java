package com.parting.dippin.api.game.dto.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameProcessReqDto {

    String time;
    double latitude;
    double longitude;
    double distance;

    double avgSpeed;
    double maxSpeed;
}
