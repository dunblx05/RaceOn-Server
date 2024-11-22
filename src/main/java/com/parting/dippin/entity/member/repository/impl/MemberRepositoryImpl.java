package com.parting.dippin.entity.member.repository.impl;


import static com.parting.dippin.entity.friends.QFriendsEntity.friendsEntity;
import static com.parting.dippin.entity.member.QMemberEntity.memberEntity;

import com.parting.dippin.domain.member.dto.MemberDto;
import com.parting.dippin.entity.member.QMemberEntity;
import com.parting.dippin.entity.member.repository.QMemberRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberRepositoryImpl extends QuerydslRepositorySupport
    implements QMemberRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QMemberEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<MemberDto> findMemberAndIsFriendByNicknameAndMemberId(String nickname,
        int myMemberId) {

        NumberExpression<Integer> similarity = new CaseBuilder()
            .when(memberEntity.nickname.eq(nickname)).then(0)
            .when(memberEntity.nickname.like(nickname + '%')).then(1)
            .when(memberEntity.nickname.like('%' + nickname)).then(2)
            .when(memberEntity.nickname.contains(nickname)).then((3))
            .otherwise(4);

        return jpaQueryFactory
            .from(memberEntity)
            .select(Projections.constructor(MemberDto.class, memberEntity,
                friendsEntity.friendId))
            .leftJoin(friendsEntity).on(friendsEntity.memberId.eq(myMemberId)
                .and(memberEntity.memberId.eq(friendsEntity.friendId)))
            .where(memberEntity.memberId.notIn(List.of(myMemberId))
                .and(memberEntity.nickname.contains(nickname)))
            .orderBy(similarity.asc())
            .fetch();
    }
}