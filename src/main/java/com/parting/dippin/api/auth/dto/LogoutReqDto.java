package com.parting.dippin.api.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutReqDto {
    private String accessToken;
    private String refreshToken;
    private String fcmToken;

    @Builder
    private LogoutReqDto(String accessToken, String refreshToken, String fcmToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.fcmToken = fcmToken;
    }
}
