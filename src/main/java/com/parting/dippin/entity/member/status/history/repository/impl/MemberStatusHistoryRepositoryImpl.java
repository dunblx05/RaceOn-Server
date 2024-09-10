package com.parting.dippin.entity.member.status.history.repository.impl;

import com.parting.dippin.entity.member.status.history.repository.QMemberStatusHistoryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberStatusHistoryRepositoryImpl extends QuerydslRepositorySupport implements
    QMemberStatusHistoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberStatusHistoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QMemberStatusHistoryRepository.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
