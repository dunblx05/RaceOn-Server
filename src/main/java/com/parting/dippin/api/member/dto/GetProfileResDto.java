package com.parting.dippin.api.member.dto;

import com.parting.dippin.entity.member.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetProfileResDto {
    int memberId;
    String nickname;
    String profileImageUrl;
    String memberCode;

    @Builder
    private GetProfileResDto(int memberId, String nickname, String profileImageUrl, String memberCode) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.memberCode = memberCode;
    }

    private GetProfileResDto(MemberEntity member) {
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileImageUrl();
        this.memberCode = member.getMemberCode();
    }

    public static GetProfileResDto from(MemberEntity member) {
        return new GetProfileResDto(member);
    }
}
