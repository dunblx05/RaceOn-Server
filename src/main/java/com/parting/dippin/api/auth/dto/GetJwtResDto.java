package com.parting.dippin.api.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GetJwtResDto {
    private final String accessToken;
    private final String refreshToken;

    @Builder
    private GetJwtResDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
