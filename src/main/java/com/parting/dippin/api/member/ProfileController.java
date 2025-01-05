package com.parting.dippin.api.member;

import com.parting.dippin.api.member.dto.GetProfileResDto;
import com.parting.dippin.api.member.dto.PatchProfileImageResDto;
import com.parting.dippin.api.member.dto.PatchUpdateProfileReqDto;
import com.parting.dippin.api.member.service.ProfileService;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import com.parting.dippin.domain.member.service.ProfileUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("members")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileUpdateService profileUpdateService;

    @GetMapping("{memberId}")
    public BaseResponse<GetProfileResDto> getProfile(
            @LoggedInMemberId Integer memberId,
            @PathVariable("memberId") Integer pMemberId
    ) {
        GetProfileResDto profile = profileService.getProfile(memberId);

        return BaseResponse.ok(profile);
    }

    @PatchMapping("{memberId}/profile-image")
    public BaseResponse<PatchProfileImageResDto> updateProfileImage() {
        PatchProfileImageResDto patchProfileImageResDto = profileUpdateService.updateProfileImage();

        return BaseResponse.ok(patchProfileImageResDto);
    }

    @PatchMapping("{memberId}")
    public BaseResponse<Void> updateProfile(
            @LoggedInMemberId Integer memberId,
            @RequestBody @Validated PatchUpdateProfileReqDto dto
    ) {
        profileUpdateService.updateProfile(memberId, dto.getNewProfileUrl(), dto.getUsername());

        return BaseResponse.ok();
    }
}
