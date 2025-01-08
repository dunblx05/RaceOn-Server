package com.parting.dippin.domain.member.dto;

import com.parting.dippin.entity.member.enums.SocialProvider;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Getter
@NoArgsConstructor
public class MemberRegisterDto {

    @NotNull
    SocialProvider socialProvider;
    @NotNull
    String idToken;
    @Nullable
    @Size(max = 20, message = "닉네임은 최대 20자입니다.")
    private String nickname;
    @Nullable
    @URL(message = "URL 형식이 아닙니다.")
    private String profileImageUrl;

    @Builder
    private MemberRegisterDto(
            @NotNull SocialProvider socialProvider,
            @NotNull String idToken,
            String nickname,
            String profileImageUrl
    ) {
        this.socialProvider = socialProvider;
        this.idToken = idToken;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
