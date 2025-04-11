package com.parting.dippin.entity.game.enums;

public enum ProgressStatus {
    MATCHING,
    FAILED_MATCHING,
    ONGOING,
    FINISHED,
    STOPPED,
    TIME_LIMIT_EXCEED;

    public boolean isInvitable() {
        return this == FINISHED;
    }
}
