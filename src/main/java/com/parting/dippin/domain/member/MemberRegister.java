package com.parting.dippin.domain.member;

import com.parting.dippin.core.auth.oauth.IdTokenParser;
import com.parting.dippin.domain.member.dto.MemberRegisterDto;
import com.parting.dippin.domain.member.service.MemberCodeGeneratorService;
import com.parting.dippin.domain.member.service.MemberRegisterService;
import com.parting.dippin.domain.member.service.MemberRegistrationValidator;
import com.parting.dippin.domain.member.service.NicknameGeneratorService;
import com.parting.dippin.entity.member.enums.SocialProvider;
import java.util.Optional;
import lombok.Getter;

@Getter
public class MemberRegister {

    private static final String DEFAULT_PROFILE_IMAGE_URL = "https://race-on.s3.ap-northeast-2.amazonaws.com/profileimage/basic_profile.png";

    private int memberId;
    private String nickname;
    private SocialProvider socialProvider;
    private String socialId;
    private String memberCode;
    private String profileImageUrl;

    public void register(
            MemberRegisterService memberRegisterService,
            NicknameGeneratorService nicknameGeneratorService,
            MemberCodeGeneratorService memberCodeGeneratorService,
            MemberRegistrationValidator memberRegistrationValidator,
            IdTokenParser idTokenParser,
            MemberRegisterDto memberRegisterDto
    ) {
        this.socialId = idTokenParser.extractSocialId(
                memberRegisterDto.getIdToken(),
                memberRegisterDto.getSocialProvider()
        );
        socialProvider = memberRegisterDto.getSocialProvider();
        nickname = Optional.ofNullable(memberRegisterDto.getNickname())
                .orElseGet(nicknameGeneratorService::generate);
        profileImageUrl = Optional.ofNullable(memberRegisterDto.getProfileImageUrl())
                .orElse(DEFAULT_PROFILE_IMAGE_URL);
        memberCode = memberCodeGeneratorService.generate();

        memberRegistrationValidator.validateMemberDoesNotExist(socialId, socialProvider);

        memberId = memberRegisterService.register(this);
    }

    public void register(
            MemberRegisterService memberRegisterService,
            NicknameGeneratorService nicknameGeneratorService,
            MemberCodeGeneratorService memberCodeGeneratorService,
            MemberRegistrationValidator memberRegistrationValidator,
            SocialProvider socialProvider,
            String socialId
    ) {
        this.socialId = socialId;
        this.socialProvider = socialProvider;
        nickname = nicknameGeneratorService.generate();
        profileImageUrl = DEFAULT_PROFILE_IMAGE_URL;
        memberCode = memberCodeGeneratorService.generate();

        memberRegistrationValidator.validateMemberDoesNotExist(socialId, socialProvider);

        memberId = memberRegisterService.register(this);
    }
}
