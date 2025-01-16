package com.parting.dippin.entity.game.repository.impl;

import com.parting.dippin.entity.game.QGameEntity;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.repository.QGameRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class GameRepositoryImpl extends QuerydslRepositorySupport implements QGameRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public GameRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QGameEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public void changeGameStatus(int gameId, ProgressStatus progressStatus) {
        QGameEntity game = QGameEntity.gameEntity;
        jpaQueryFactory.update(game)
            .set(game.progressStatus, progressStatus)
            .where(game.gameId.eq(gameId))
            .execute();
    }
}
