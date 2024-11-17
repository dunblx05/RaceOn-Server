package com.parting.dippin.entity.friends;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class FriendsId implements Serializable {
    private Integer memberId;

    private Integer friendId;

    public FriendsId(Integer memberId, Integer friendId) {
        this.memberId = memberId;
        this.friendId = friendId;
    }
}
