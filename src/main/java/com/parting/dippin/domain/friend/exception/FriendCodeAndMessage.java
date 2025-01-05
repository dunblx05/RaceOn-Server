package com.parting.dippin.domain.friend.exception;

import com.parting.dippin.core.exception.CodeAndMessage;

// 도메인 코드 : FR
public enum FriendCodeAndMessage implements CodeAndMessage {
    ALREADY_FRIEND_EXCEPTION(false, 400, "FRBR01", "이미 추가된 친구입니다."),
    NOT_FRIENDS_EXCEPTION(false, 400, "FRBR02", "친구 관계가 아닙니다.");

    private final boolean success;
    private final int httpStatusCode;
    private final String code;
    private final String message;

    FriendCodeAndMessage(
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
