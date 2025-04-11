package com.parting.dippin.entity.game;

import static com.parting.dippin.entity.game.enums.MemberGameStatus.INVITABLE;

import com.parting.dippin.entity.game.enums.MemberGameStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member_game_status")
@Entity
public class MemberGameStatusEntity {

    @Id
    @Column(name = "member_id", columnDefinition = "int(11)")
    private Integer memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress_status", columnDefinition = "char(30) default 'INVITABLE'", nullable = false)
    private MemberGameStatus progressStatus;

    @Version
    private Long version;

    public MemberGameStatusEntity(Integer memberId) {
        this.memberId = memberId;
        this.progressStatus = INVITABLE;
    }

    public void updateStatus(MemberGameStatus status) {
        this.progressStatus = status;
    }

    public boolean isInvitable() {
        return progressStatus == INVITABLE;
    }
}
