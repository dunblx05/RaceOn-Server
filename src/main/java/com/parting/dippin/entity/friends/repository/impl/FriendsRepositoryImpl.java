package com.parting.dippin.entity.friends.repository.impl;

import static com.parting.dippin.entity.friends.QFriendsEntity.friendsEntity;
import static com.parting.dippin.entity.member.QMemberEntity.memberEntity;
import static com.querydsl.core.types.ExpressionUtils.and;
import static com.querydsl.core.types.ExpressionUtils.or;

import com.parting.dippin.domain.friend.dto.FriendDto;
import com.parting.dippin.entity.friends.QFriendsEntity;
import com.parting.dippin.entity.friends.repository.QFriendsRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class FriendsRepositoryImpl extends QuerydslRepositorySupport
    implements QFriendsRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public FriendsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QFriendsEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<FriendDto> findByMemberId(int memberId) {
        return jpaQueryFactory
            .select(Projections.constructor(FriendDto.class, memberEntity, friendsEntity))
            .from(friendsEntity)
            .innerJoin(memberEntity).on(friendsEntity.friendId.eq(memberEntity.memberId))
            .where(friendsEntity.memberId.eq(memberId))
            .fetch();
    }

    @Override
    public boolean existsFriendsByMyMemberIdAndMemberId(int myMemberId, int memberId) {
        Integer fetchOne = jpaQueryFactory
            .selectOne()
            .from(friendsEntity)
            .where(
                or(
                    and(
                        friendsEntity.memberId.eq(myMemberId),
                        friendsEntity.friendId.eq(memberId)
                    ),
                    and(
                        friendsEntity.memberId.eq(memberId),
                        friendsEntity.friendId.eq(myMemberId)
                    )
                )
            ).fetchFirst();

        return fetchOne != null;
    }
}
