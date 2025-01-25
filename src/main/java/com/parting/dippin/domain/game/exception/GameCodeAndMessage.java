package com.parting.dippin.domain.game.exception;

import com.parting.dippin.core.exception.CodeAndMessage;

public enum GameCodeAndMessage implements CodeAndMessage {
    NOT_AVAILABLE_GAME(false, 400, "GABR01", "진행 가능한 게임이 아닙니다."),
    NOT_COMPLETED_GAME(false, 400, "GABR02", "아직 완주하지 않았습니다."),
    NOT_ONGOING_GAME(false, 400, "GABR03", "진행 중인 게임이 아닙니다."),
    NOT_MATCHING_GAME(false, 400, "GABR04", "매칭 중인 게임이 아닙니다."),
    NOT_GAME_MEMBER(false, 403, "GAFB01", "해당 게임의 구성원이 아닙니다."),
    GAME_NOT_FOUND(false, 404, "GANF01", "게임 정보를 찾을 수 없습니다."),
    ALREADY_PARTICIPANT_MEMBER(false, 409, "GACF01", "이미 참여 중인 게임입니다."),
    ALREADY_ONGOING_GAME(false, 409, "GACF02", "이미 진행 중인 게임입니다."),
    ALREADY_MATCHING_OR_GAMING_MEMBER(false, 409, "GACF03", "이미 게임중인 멤버입니다."),
    ALREADY_FINISHED_GAME(false, 410, "GAGO01", "이미 종료된 게임입니다.");

    private final boolean success;
    private final int httpStatusCode;
    private final String code;
    private final String message;

    GameCodeAndMessage(
            boolean success,
            final int httpStatusCode,
            final String code,
            final String message
    ) {
        this.success = success;
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    public int httpStatusCode() {
        return httpStatusCode;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
