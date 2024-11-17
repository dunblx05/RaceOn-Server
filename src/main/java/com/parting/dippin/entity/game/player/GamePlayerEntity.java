package com.parting.dippin.entity.game.player;

import com.parting.dippin.core.base.BaseEntity;
import com.parting.dippin.entity.game.GameEntity;
import com.parting.dippin.entity.game.geo.GeoCoordinatesEntity;
import com.parting.dippin.entity.game.player.enums.ResultStatus;
import com.parting.dippin.entity.member.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "game_player")
@Entity
@IdClass(GamePlayerId.class)
public class GamePlayerEntity extends BaseEntity {

    @Id
    @Column(name = "game_id")
    private Integer gameId;

    @Id
    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "distance", columnDefinition = "DECIMAL(6,3) default 0", nullable = false)
    private Double distance;

    @Column(name = "finished_time", columnDefinition = "VARCHAR(8)")
    private String finishedTime;

    @Column(name = "avg_speed", columnDefinition = "DECIMAL(6,3) default 0", nullable = false)
    private Double avgSpeed;

    @Column(name = "max_speed", columnDefinition = "DECIMAL(6,3) default 0", nullable = false)
    private Double maxSpeed;

    @Enumerated(EnumType.STRING)
    @Column(name = "result_status", columnDefinition = "char(30) default 'UNDECIDED'", nullable = false)
    private ResultStatus resultStatus;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @MapsId("gameId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "game_id", referencedColumnName = "game_id"),
        @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
    private List<GeoCoordinatesEntity> geoCoordinatesEntities = new ArrayList<>();
}
