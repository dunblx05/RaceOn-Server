package com.parting.dippin.entity.game.geo;

import com.parting.dippin.core.base.BaseEntity;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "geo_coordinates")
@Entity
@IdClass(GeoCoordinatesId.class)
public class GeoCoordinatesEntity extends BaseEntity {

    @Id
    @Column(name = "game_id")
    private Integer gameId;

    @Id
    @Column(name = "member_id")
    private Integer memberId;

    @Id
    @Column(name = "time", columnDefinition = "VARCHAR(8)", nullable = false)
    private String time;

    @Column(name = "latitude", columnDefinition = "DECIMAL(9,6) DEFAULT 0", nullable = false)
    private Double latitude;

    @Column(name = "longitude", columnDefinition = "DECIMAL(9,6) DEFAULT 0", nullable = false)
    private Double longitude;

    @Column(name = "distance", columnDefinition = "DECIMAL(6,3) default 0", nullable = false)
    private Double distance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "game_id", referencedColumnName = "game_id"),
        @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    })
    private GamePlayerEntity gamePlayerEntity;
}
