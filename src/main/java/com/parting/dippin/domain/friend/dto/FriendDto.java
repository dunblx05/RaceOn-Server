package com.parting.dippin.domain.friend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parting.dippin.entity.connection.ConnectionStatusEntity;
import com.parting.dippin.entity.member.MemberEntity;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class FriendDto {

    Integer friendId;
    String friendNickname;
    String profileImageUrl;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime lastActiveAt;
    boolean isPlaying;

    public FriendDto(
            Integer friendId,
            String friendNickname,
            String profileImageUrl,
            LocalDateTime lastActiveAt
    ) {
        this.friendId = friendId;
        this.friendNickname = friendNickname;
        this.profileImageUrl = profileImageUrl;
        this.lastActiveAt = lastActiveAt;
    }

    private FriendDto(MemberEntity memberEntity, ConnectionStatusEntity connectionStatus) {
        this.friendId = memberEntity.getMemberId();
        this.friendNickname = memberEntity.getNickname();
        this.profileImageUrl = memberEntity.getProfileImageUrl();
        this.lastActiveAt = connectionStatus.getLastActiveAt();
        this.isPlaying = connectionStatus.isPlaying();
    }

    private FriendDto(MemberEntity memberEntity) {
        this.friendId = memberEntity.getMemberId();
        this.friendNickname = memberEntity.getNickname();
        this.profileImageUrl = memberEntity.getProfileImageUrl();
        this.lastActiveAt = memberEntity.getLastActiveAt();
        this.isPlaying = false;
    }

    public static FriendDto of(MemberEntity memberEntity, ConnectionStatusEntity connectionStatus) {
        if (connectionStatus == null) {
            return new FriendDto(memberEntity);
        }else{
            return new FriendDto(memberEntity, connectionStatus);
        }
    }
}
