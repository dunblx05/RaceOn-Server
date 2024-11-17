package com.parting.dippin.entity.term.repository.impl;

import com.parting.dippin.entity.term.QTermEntity;
import com.parting.dippin.entity.term.repository.QTermRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class TermRepositoryImpl extends QuerydslRepositorySupport implements QTermRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public TermRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QTermEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
