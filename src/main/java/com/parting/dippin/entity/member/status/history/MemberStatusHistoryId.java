package com.parting.dippin.entity.member.status.history;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class MemberStatusHistoryId implements Serializable {

    private Integer memberId;

    @Column(name = "created_at", columnDefinition = "datetime", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public MemberStatusHistoryId(Integer memberId) {
        ZonedDateTime kstNow = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        this.memberId = memberId;
        this.createdAt = kstNow.toLocalDateTime();
    }
}
