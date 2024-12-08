package com.parting.dippin.api.member.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PatchUpdateProfileReqDto {

    //Todo nullable 동작 확인필요.
    @Nullable
    String newProfileUrl;

    @Nullable
    String username;

    @Builder
    private PatchUpdateProfileReqDto(String newProfileUrl, String username) {
        this.newProfileUrl = newProfileUrl;
        this.username = username;
    }
}
