package com.parting.dippin.api.member.dto;

import com.parting.dippin.domain.member.dto.MemberDto;
import java.util.List;
import lombok.Getter;

@Getter
public class GetMembersResDto {
    List<MemberDto> members;

    public GetMembersResDto(List<MemberDto> members) {
        this.members = members;
    }
}