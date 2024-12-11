package com.parting.dippin.entity.friends;

import com.parting.dippin.core.base.BaseEntity;
import com.parting.dippin.domain.friend.FriendAdder;
import com.parting.dippin.entity.member.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "friends")
@Entity
@IdClass(FriendsId.class)
public class FriendsEntity extends BaseEntity {

    @Id
    @Column(name = "member_id")
    private Integer memberId;

    @Id
    @Column(name = "friend_id")
    private Integer friendId;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @MapsId("friendId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private MemberEntity friend;

    public static List<FriendsEntity> from(FriendAdder friendAdder) {
        List<FriendsEntity> entities = new ArrayList<>();

        FriendsEntity entity1 = new FriendsEntity();
        entity1.friendId = friendAdder.getFriendId();
        entity1.memberId = friendAdder.getMemberId();
        entity1.member = MemberEntity.from(entity1.memberId);
        entity1.friend = MemberEntity.from(entity1.friendId);

        FriendsEntity entity2 = new FriendsEntity();
        entity2.friendId = friendAdder.getMemberId();
        entity2.memberId = friendAdder.getFriendId();
        entity2.member = MemberEntity.from(entity1.friendId);
        entity2.friend = MemberEntity.from(entity1.memberId);

        entities.add(entity1);
        entities.add(entity2);

        return entities;
    }
}
