package com.parting.dippin.entity.game.player.repository.impl;

import static com.parting.dippin.entity.game.player.QGamePlayerEntity.gamePlayerEntity;
import static com.querydsl.core.types.ExpressionUtils.and;
import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.querydsl.core.types.dsl.Expressions.anyOf;

import com.parting.dippin.entity.game.enums.PlayerStatus;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.game.player.QGamePlayerEntity;
import com.parting.dippin.entity.game.player.enums.ResultStatus;
import com.parting.dippin.entity.game.player.repository.QGamePlayerRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Slf4j
public class GamePlayerRepositoryImpl extends QuerydslRepositorySupport implements
    QGamePlayerRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public GamePlayerRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(QGamePlayerEntity.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public boolean existsMatchingPlayer(int memberId, int friendId) {
        GamePlayerEntity fetchOne = jpaQueryFactory
            .select(gamePlayerEntity)
            .from(gamePlayerEntity)
            .innerJoin(gamePlayerEntity.game)
            .where(
                and(
                    gamePlayerEntity.game.progressStatus.in(
                        List.of(ProgressStatus.ONGOING, ProgressStatus.MATCHING)),
                    anyOf(
                        allOf(
                            gamePlayerEntity.memberId.eq(memberId),
                            gamePlayerEntity.resultStatus.eq(ResultStatus.UNDECIDED),
                            gamePlayerEntity.playerStatus.notIn(List.of(PlayerStatus.REJECT))
                        ),
                        allOf(
                            gamePlayerEntity.memberId.eq(friendId),
                            gamePlayerEntity.resultStatus.eq(ResultStatus.UNDECIDED),
                            gamePlayerEntity.playerStatus.notIn(List.of(PlayerStatus.REJECT))
                        )
                    )
                )
            )
            .fetchFirst();

        return fetchOne != null;
    }
}
