package com.parting.dippin.entity.game.player;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class GamePlayerId implements Serializable {

    private Integer gameId;

    private Integer memberId;

    public GamePlayerId(Integer gameId, Integer memberId) {
        this.gameId = gameId;
        this.memberId = memberId;
    }
}
