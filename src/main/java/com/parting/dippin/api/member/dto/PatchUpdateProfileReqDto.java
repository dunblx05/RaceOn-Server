package com.parting.dippin.api.member.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class PatchUpdateProfileReqDto {

    @Nullable
    @URL(message = "URL 형식이 아닙니다.")
    String newProfileUrl;

    @Nullable
    @Size(max = 20, message = "닉네임은 최대 20자입니다.")
    String username;

    @Builder
    private PatchUpdateProfileReqDto(String newProfileUrl, String username) {
        this.newProfileUrl = newProfileUrl;
        this.username = username;
    }
}
