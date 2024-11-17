package com.parting.dippin.entity.member;

import com.parting.dippin.core.base.BaseEntity;
import com.parting.dippin.entity.friends.FriendsEntity;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.member.enums.MemberStatus;
import com.parting.dippin.entity.token.FcmTokenEntity;
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
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
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

    @Column(name = "nickname", columnDefinition = "varchar(20)", unique = true)
    private String nickname;

    @Column(name = "profile_image_url", columnDefinition = "text")
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status", columnDefinition = "char(30) default 'ACTIVE'", nullable = false)
    private MemberStatus memberStatus;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private List<FcmTokenEntity> fcmTokenEntities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private List<GamePlayerEntity> gamePlayerEntities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private List<FriendsEntity> friendsEntities = new ArrayList<>();
}
