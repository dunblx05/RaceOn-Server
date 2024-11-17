package com.parting.dippin.entity.friends;

import com.parting.dippin.core.base.BaseEntity;
import com.parting.dippin.entity.member.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "friends")
@Entity
@IdClass(FriendsId.class)
public class FriendsEntity extends BaseEntity {

    @Id
    @Column(name = "member_id")
    private Integer memberId;

    @Id
    @Column(name = "friend_id")
    private Integer friendId;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @MapsId("friendId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private MemberEntity friend;
}
