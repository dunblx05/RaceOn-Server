package com.parting.dippin.entity.game.geo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class GeoCoordinatesId {

    private Integer gameId;

    private Integer memberId;

    private String time;

    public GeoCoordinatesId(Integer gameId, Integer memberId, String time) {
        this.gameId = gameId;
        this.memberId = memberId;
        this.time = time;
    }
}
