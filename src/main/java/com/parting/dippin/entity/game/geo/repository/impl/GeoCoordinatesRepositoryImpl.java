package com.parting.dippin.entity.game.geo.repository.impl;

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
}
