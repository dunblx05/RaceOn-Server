package com.parting.dippin.api.auth.dto;

import com.parting.dippin.entity.member.enums.SocialProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLoginReqDto {

    private String idToken;
    private SocialProvider socialProvider;

    @Builder
    private PostLoginReqDto(String idToken, SocialProvider socialProvider) {
        this.idToken = idToken;
        this.socialProvider = socialProvider;
    }
}
