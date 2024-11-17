package com.parting.dippin.entity.token.repository.impl;

import com.parting.dippin.entity.token.QFcmTokenEntity;
import com.parting.dippin.entity.token.repository.QFcmTokenRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class FcmTokenRepositoryImpl extends QuerydslRepositorySupport implements
    QFcmTokenRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public FcmTokenRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QFcmTokenEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
