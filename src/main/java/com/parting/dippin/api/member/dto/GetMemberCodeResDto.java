package com.parting.dippin.api.member.dto;

import lombok.Getter;

@Getter
public class GetMemberCodeResDto {

    String memberCode;

    public GetMemberCodeResDto(String memberCode) {
        this.memberCode = memberCode;
    }
}
