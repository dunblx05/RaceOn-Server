package com.parting.dippin.entity.term.repository.impl;

import com.parting.dippin.entity.member.QMember;
import com.parting.dippin.entity.term.QTerm;
import com.parting.dippin.entity.term.repository.QTermRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TermRepositoryImpl extends QuerydslRepositorySupport implements QTermRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public TermRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QTerm.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
