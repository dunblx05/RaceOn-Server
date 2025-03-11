package com.parting.dippin.entity.game;

import com.parting.dippin.core.base.BaseEntity;
import com.parting.dippin.entity.game.enums.ProgressStatus;
import com.parting.dippin.entity.game.enums.Type;
import com.parting.dippin.entity.game.player.GamePlayerEntity;
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
import java.time.LocalDateTime;
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

    public static final long START_DELAY_SECONDS = 3;

    @Id
    @Column(name = "game_id", columnDefinition = "int(11)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;

    @Column(name = "distance", columnDefinition = "DECIMAL(6, 3) DEFAULT 0", nullable = false)
    private Double distance;

    @Column(name = "time_limit", columnDefinition = "int(11)", nullable = false)
    private Integer timeLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress_status", columnDefinition = "char(30) default 'MATCHING'", nullable = false)
    private ProgressStatus progressStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "char(30) default 'DEFAULT'", nullable = false)
    private Type type;

    @Column(name = "start_time", columnDefinition = "datetime")
    private LocalDateTime startTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private List<GamePlayerEntity> gamePlayerEntities = new ArrayList<>();

    public static GameEntity from(int gameId) {
        GameEntity game = new GameEntity();
        game.gameId = gameId;

        return game;
    }

    public static GameEntity generate(double distance, int timeLimit) {
        GameEntity game = new GameEntity();
        game.distance = distance;
        game.timeLimit = timeLimit;
        game.progressStatus = ProgressStatus.MATCHING;
        game.type = Type.DEFAULT;

        return game;
    }

    public LocalDateTime start() {
        this.progressStatus = ProgressStatus.ONGOING;
        this.startTime = LocalDateTime.now().plusSeconds(START_DELAY_SECONDS).withNano(0);

        return startTime;
    }

    public boolean isFinished(double distance) {
        return distance >= this.distance;
    }

    public void finish() {
        this.progressStatus = ProgressStatus.FINISHED;
    }

    public void stop() {
        this.progressStatus = ProgressStatus.STOPPED;
    }

    public boolean isNotMatching() {
        return !this.progressStatus.equals(ProgressStatus.MATCHING);
    }

    public void failMatching() {
        this.progressStatus = ProgressStatus.FAILED_MATCHING;
    }

    public void cancelGame() {
        this.progressStatus = ProgressStatus.FAILED_MATCHING;
    }

    public boolean isExceedTimeLimit(LocalDateTime now) {
        return this.startTime.plusMinutes(timeLimit).isBefore(now);
    }

    public void exceedTimeLimit() {
        this.progressStatus = ProgressStatus.TIME_LIMIT_EXCEED;
    }
}
