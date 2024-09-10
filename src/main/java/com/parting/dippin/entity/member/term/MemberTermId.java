package com.parting.dippin.entity.member.term;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class MemberTermId implements Serializable {

    private Integer memberId;

    private Integer termId;

    public MemberTermId(Integer memberId, Integer termId) {
        this.memberId = memberId;
        this.termId = termId;
    }
}