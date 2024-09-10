package com.parting.dippin.entity.sms.authentication.code.repository.impl;

import static com.parting.dippin.entity.member.QMember.member;

import com.parting.dippin.domain.member.dto.MemberDto;
import com.parting.dippin.entity.member.QMember;
import com.parting.dippin.entity.sms.authentication.code.QSmsAuthenticationCode;
import com.parting.dippin.entity.sms.authentication.code.repository.QSmsAuthenticationCodeRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class SmsAuthenticationCodeRepositoryImpl extends QuerydslRepositorySupport implements
    QSmsAuthenticationCodeRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public SmsAuthenticationCodeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QSmsAuthenticationCode.class);
        this.jpaQueryFactory = jpaQueryFactory;

    }
}
