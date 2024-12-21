package com.parting.dippin.api.game.dto;

import lombok.Getter;

@Getter
public class PostGameResDto {

    GameGeneratedInfoDto gameInfo;

    public PostGameResDto(GameGeneratedInfoDto gameInfo) {
        this.gameInfo = gameInfo;
    }
}
