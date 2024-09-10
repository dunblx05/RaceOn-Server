package com.parting.dippin.entity.member.fcm.token;

import com.parting.dippin.entity.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member_fcm_token")
@Entity
public class MemberFcmToken {

    @EmbeddedId
    private MemberFcmTokenId id;

    @Column(name = "created_at", columnDefinition = "datetime", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @PrePersist
    protected void onCreate() {

        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
