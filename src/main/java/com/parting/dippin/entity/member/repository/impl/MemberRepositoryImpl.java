package com.parting.dippin.entity.member.repository.impl;


import static com.parting.dippin.entity.member.QMemberEntity.memberEntity;

import com.parting.dippin.domain.member.dto.MemberDto;
import com.parting.dippin.entity.member.QMemberEntity;
import com.parting.dippin.entity.member.repository.QMemberRepository;
import com.querydsl.core.types.Projections;
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
    public List<MemberDto> findMemberList() {
        return jpaQueryFactory.from(memberEntity).select(Projections.constructor(MemberDto.class,
                memberEntity))
            .fetch();
    }
}
