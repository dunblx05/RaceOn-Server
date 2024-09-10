package com.parting.dippin.entity.member.status.history;

import com.parting.dippin.entity.member.Member;
import com.parting.dippin.entity.member.enums.MemberStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member_status_history")
@Entity
public class MemberStatusHistory {

    @EmbeddedId
    private MemberStatusHistoryId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status", columnDefinition = "char(30) default 'ACTIVE'", nullable = false )
    private MemberStatus memberStatus;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
