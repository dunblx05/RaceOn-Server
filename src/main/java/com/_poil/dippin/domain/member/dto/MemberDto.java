package com._poil.dippin.domain.member.dto;

import com._poil.dippin.entity.Member;
import lombok.Getter;

@Getter
public class MemberDto {
    int memberId;
    String memberEmail;
    String memberName;

    public MemberDto(Member member) {
        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
    }
}
