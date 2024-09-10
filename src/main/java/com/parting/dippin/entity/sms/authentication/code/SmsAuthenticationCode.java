package com.parting.dippin.entity.sms.authentication.code;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "sms_authentication_code")
@Entity
public class SmsAuthenticationCode {

    @EmbeddedId
    private SmsAuthenticationCodeId id;

    @Column(name = "authentication_code", columnDefinition = "char(6)", nullable = false, updatable = false)
    private String authenticationCode;
}
