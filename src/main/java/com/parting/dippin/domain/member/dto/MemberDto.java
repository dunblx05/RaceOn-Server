package com.parting.dippin.domain.member.dto;

import com.parting.dippin.entity.member.MemberEntity;
import lombok.Getter;

@Getter
public class MemberDto {
    Integer memberId;

    public MemberDto(MemberEntity member) {
        this.memberId = member.getMemberId();
    }
}