package com.parting.dippin.entity.member;

import com.parting.dippin.core.base.BaseEntity;
import com.parting.dippin.domain.member.MemberRegister;
import com.parting.dippin.entity.friends.FriendsEntity;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.member.enums.MemberStatus;
import com.parting.dippin.entity.member.enums.SocialProvider;
import com.parting.dippin.entity.token.FcmTokenEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
@Entity
public class MemberEntity extends BaseEntity {

    @Id
    @Column(name = "member_id", columnDefinition = "int(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @Column(name = "nickname", columnDefinition = "varchar(20)", nullable = false)
    private String nickname;

    @Column(name = "profile_image_url", columnDefinition = "text", nullable = false)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status", columnDefinition = "char(30) default 'ACTIVE'", nullable = false)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_provider", columnDefinition = "varchar(10)", nullable = false)
    private SocialProvider socialProvider;

    @Column(name = "social_id", columnDefinition = "varchar(50)", nullable = false)
    private String socialId;

    @Column(name = "member_code", columnDefinition = "varchar(6)", nullable = false, unique = true)
    private String memberCode;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private List<FcmTokenEntity> fcmTokenEntities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private List<GamePlayerEntity> gamePlayerEntities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private List<FriendsEntity> friendsEntities = new ArrayList<>();

    @Column(name = "last_active_at", columnDefinition = "datetime", nullable = false)
    private LocalDateTime lastActiveAt;

    @Builder
    public MemberEntity(
            Integer memberId,
            String nickname,
            String profileImageUrl,
            MemberStatus memberStatus,
            SocialProvider socialProvider,
            String socialId,
            String memberCode,
            List<FcmTokenEntity> fcmTokenEntities,
            List<GamePlayerEntity> gamePlayerEntities,
            List<FriendsEntity> friendsEntities,
            LocalDateTime lastActiveAt
    ) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.memberStatus = memberStatus;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.memberCode = memberCode;
        this.fcmTokenEntities = fcmTokenEntities;
        this.gamePlayerEntities = gamePlayerEntities;
        this.friendsEntities = friendsEntities;
        this.lastActiveAt = lastActiveAt;
    }

    public MemberEntity(MemberEntity memberEntity) {
        this.memberId = memberEntity.getMemberId();
        this.nickname = memberEntity.getNickname();
        this.profileImageUrl = memberEntity.getProfileImageUrl();
        this.memberStatus = memberEntity.getMemberStatus();
        this.socialProvider = memberEntity.getSocialProvider();
        this.socialId = memberEntity.getSocialId();
        this.memberCode = memberEntity.getMemberCode();
        this.lastActiveAt = memberEntity.getLastActiveAt();
    }

    public void updateProfile(String newProfileUrl, String newNickname) {
        if (StringUtils.isNotBlank(newProfileUrl)) {
            this.profileImageUrl = newProfileUrl;
        }

        if (StringUtils.isNotBlank(newNickname)) {
            this.nickname = newNickname;
        }
    }
  
    public static MemberEntity from(MemberRegister memberRegister) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.nickname = memberRegister.getNickname();
        memberEntity.socialProvider = memberRegister.getSocialProvider();
        memberEntity.socialId = memberRegister.getSocialId();
        memberEntity.memberCode = memberRegister.getMemberCode();

        return memberEntity;
    }

    public static MemberEntity from(int memberId) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.memberId = memberId;

        return memberEntity;
    }

    public void updateLastActiveAt(LocalDateTime lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }
}
