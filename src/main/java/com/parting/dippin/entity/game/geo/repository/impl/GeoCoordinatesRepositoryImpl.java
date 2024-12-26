package com.parting.dippin.entity.game.geo.repository.impl;

import static com.parting.dippin.entity.game.geo.QGeoCoordinatesEntity.geoCoordinatesEntity;

import com.parting.dippin.entity.game.geo.QGeoCoordinatesEntity;
import com.parting.dippin.entity.game.geo.repository.QGeoCoordinatesRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class GeoCoordinatesRepositoryImpl extends QuerydslRepositorySupport implements
    QGeoCoordinatesRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public GeoCoordinatesRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QGeoCoordinatesEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public String findTimeByIdOrderByTimeDESC(int gameId, int memberId) {
        return jpaQueryFactory
            .from(geoCoordinatesEntity)
            .select(geoCoordinatesEntity.time)
            .where(geoCoordinatesEntity.gameId.eq(gameId)
                .and(geoCoordinatesEntity.memberId.eq(memberId)))
            .orderBy(geoCoordinatesEntity.time.desc())
            .limit(1)
            .fetchOne();
    }
}
