package com.parting.dippin.entity.game;

import com.parting.dippin.core.base.BaseEntity;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.enums.Type;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
import com.parting.dippin.entity.member.enums.MemberStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "game")
@Entity
public class GameEntity extends BaseEntity {

    @Id
    @Column(name = "game_id", columnDefinition = "int(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;

    @Column(name = "distance", columnDefinition = "DECIMAL(3, 3) DEFAULT 0", nullable = false)
    private Double distance;

    @Column(name = "time_limit", columnDefinition = "int(11)", nullable = false)
    private Integer timeLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress_status", columnDefinition = "char(30) default 'MATCHING'", nullable = false)
    private ProgressStatus progressStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "char(30) default 'DEFAULT'", nullable = false)
    private Type type;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private List<GamePlayerEntity> gamePlayerEntities = new ArrayList<>();
}
