package com.parting.dippin.api.member;

import com.parting.dippin.api.member.dto.GetMemberCodeResDto;
import com.parting.dippin.api.member.dto.GetMembersResDto;
import com.parting.dippin.api.member.dto.PatchProfileImageResDto;
import com.parting.dippin.api.member.dto.PatchUpdateProfileReqDto;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import com.parting.dippin.domain.member.dto.MemberDto;
import com.parting.dippin.domain.member.service.MemberWithdrawService;
import com.parting.dippin.domain.member.service.ProfileUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping("members")
@RestController()
public class MemberController {

    private final MemberReader memberReader;
    private final ProfileUpdateService profileUpdateService;
    private final MemberWithdrawService memberWithdrawService;

    @GetMapping()
    public BaseResponse<GetMembersResDto> getMembers(
            @LoggedInMemberId Integer memberId,
            @RequestParam String nickname
    ) {
        List<MemberDto> members = this.memberReader.getMembers(nickname, memberId);
        GetMembersResDto resDto = new GetMembersResDto(members);

        return BaseResponse.ok(resDto);
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

    @GetMapping("/{memberId}/member-code")
    public BaseResponse<GetMemberCodeResDto> getMemberCode(
            @LoggedInMemberId Integer memberId,
            @PathVariable("memberId") String pMemberId
    ) {
        String memberCode = memberReader.getMemberCode(memberId);

        GetMemberCodeResDto getMemberCodeResDto = new GetMemberCodeResDto(memberCode);

        return BaseResponse.ok(getMemberCodeResDto);
    }

    @DeleteMapping("{memberId}")
    public BaseResponse<Void> withdraw(
            @LoggedInMemberId Integer memberId,
            @PathVariable("memberId") Integer pMemberId
    ) {
        memberWithdrawService.withdraw(memberId);

        return BaseResponse.ok();
    }
}
