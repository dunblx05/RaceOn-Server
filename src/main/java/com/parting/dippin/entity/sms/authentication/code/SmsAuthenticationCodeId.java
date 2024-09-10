package com.parting.dippin.entity.sms.authentication.code;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class SmsAuthenticationCodeId {

    @Column(name = "phone_number", columnDefinition = "varchar(30)")
    private String phoneNumber;

    @Column(name = "created_at", columnDefinition = "datetime", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public SmsAuthenticationCodeId(String phoneNumber) {
        ZonedDateTime kstNow = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        this.phoneNumber = phoneNumber;
        this.createdAt = kstNow.toLocalDateTime();
    }
}
