package com.parting.dippin.entity.token;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class FcmTokenId implements Serializable {

    private Integer memberId;

    @Column(name = "token", columnDefinition = "varchar(255)", nullable = false, updatable = false)
    private String token;

    public FcmTokenId(Integer memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }
}
