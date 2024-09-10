package com.parting.dippin.domain.member.dto;

import com.parting.dippin.entity.member.Member;
import lombok.Getter;

@Getter
public class MemberDto {
    Integer memberId;

    public MemberDto(Member member) {
        this.memberId = member.getMemberId();
    }
}
