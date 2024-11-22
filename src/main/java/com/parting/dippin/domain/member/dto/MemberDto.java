package com.parting.dippin.domain.member.dto;

import com.parting.dippin.entity.member.MemberEntity;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MemberDto {
    int memberId;
    String nickname;
    String profileImageUrl;
    boolean isFriend;

    public MemberDto(MemberEntity member, int friendId) {
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileImageUrl();

        this.isFriend = Objects.equals(member.getMemberId(), friendId);
    }
}