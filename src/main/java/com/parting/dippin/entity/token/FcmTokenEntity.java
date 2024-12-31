package com.parting.dippin.entity.token;

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
@Table(name = "member_fcm_token")
@Entity
@IdClass(FcmTokenId.class)
public class FcmTokenEntity extends BaseEntity {

    @Id
    @Column(name = "member_id")
    private Integer memberId;

    @Id
    @Column(name = "token", columnDefinition = "VARCHAR(255)", nullable = false, updatable = false)
    private String token;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    public static FcmTokenEntity of(int memberId, String token) {
        FcmTokenEntity entity = new FcmTokenEntity();
        entity.member = MemberEntity.from(memberId);
        entity.memberId = memberId;
        entity.token = token;

        return entity;
    }
}
