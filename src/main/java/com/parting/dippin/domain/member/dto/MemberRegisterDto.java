package com.parting.dippin.domain.member.dto;

import com.parting.dippin.entity.member.enums.SocialProvider;
import lombok.Getter;

@Getter
public class MemberRegisterDto {

    SocialProvider socialProvider;
    String socialId;

    MemberRegisterDto(SocialProvider socialProvider, String socialId) {
        this.socialProvider = socialProvider;
        this.socialId = socialId;
    }
}
