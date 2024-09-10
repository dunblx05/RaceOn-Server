package com.parting.dippin.entity.member.fcm.token;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class MemberFcmTokenId implements Serializable {

    private Integer memberId;

    @Column(name = "token", columnDefinition = "varchar(255)", nullable = false, updatable = false)
    private String token;

    public MemberFcmTokenId(Integer memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }
}
