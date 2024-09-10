package com.parting.dippin.entity.member.fcm.token.repository.impl;

import com.parting.dippin.entity.member.QMember;
import com.parting.dippin.entity.member.fcm.token.QMemberFcmToken;
import com.parting.dippin.entity.member.fcm.token.repository.QMemberFcmTokenRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberFcmTokenRepositoryImpl extends QuerydslRepositorySupport implements
    QMemberFcmTokenRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public MemberFcmTokenRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QMemberFcmToken.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
