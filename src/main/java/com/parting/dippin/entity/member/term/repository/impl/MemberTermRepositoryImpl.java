package com.parting.dippin.entity.member.term.repository.impl;

import com.parting.dippin.entity.member.term.QMemberTermEntity;
import com.parting.dippin.entity.member.term.repository.QMemberTermRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberTermRepositoryImpl extends QuerydslRepositorySupport implements
    QMemberTermRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberTermRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QMemberTermEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
