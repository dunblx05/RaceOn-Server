package com.parting.dippin.api.member;

import com.parting.dippin.api.member.dto.GetMembersResDto;
import com.parting.dippin.api.member.service.MemberReader;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import com.parting.dippin.domain.member.dto.MemberDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("members")
@RestController()
public class MemberController {

    private final MemberReader memberReader;

    @GetMapping()
    public BaseResponse<GetMembersResDto> getMembers(
            @LoggedInMemberId Integer memberId,
            @RequestParam String nickname
    ) {
        List<MemberDto> members = this.memberReader.getMembers(nickname, memberId);
        GetMembersResDto resDto = new GetMembersResDto(members);

        return BaseResponse.success(resDto);
    }
}
