package com.parting.dippin.entity.token.repository.impl;

import static com.parting.dippin.entity.token.QFcmTokenEntity.fcmTokenEntity;

import com.parting.dippin.entity.token.QFcmTokenEntity;
import com.parting.dippin.entity.token.repository.QFcmTokenRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class FcmTokenRepositoryImpl extends QuerydslRepositorySupport implements
    QFcmTokenRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public FcmTokenRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QFcmTokenEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<String> findTokensByMemberId(int memberId) {
        return jpaQueryFactory.from(fcmTokenEntity)
            .select(fcmTokenEntity.id.token)
            .where(fcmTokenEntity.id.memberId.eq(memberId))
            .fetch();
    }
}
