package com.parting.dippin.api.game.dto.socket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameStopReqDto {
    int requestMemberId;
    @JsonProperty("isAgree")
    boolean isAgree;
}
