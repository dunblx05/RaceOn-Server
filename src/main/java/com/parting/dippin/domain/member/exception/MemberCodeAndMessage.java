package com.parting.dippin.domain.member.exception;

import com.parting.dippin.core.exception.CodeAndMessage;

// 도메인 코드 : ME
public enum MemberCodeAndMessage implements CodeAndMessage {
    FAILED_UPLOAD_IMAGE(false, 500, "MEES01", "이미지 업로드 실패"),
    INVALID_MEMBER_CODE(false, 400, "MEBR01", "유효하지 않은 코드에요."),
    EXIST_MEMBER(false, 400, "MEBR02", "이미 가입된 유저입니다.");

    private final boolean success;
    private final int httpStatusCode;
    private final String code;
    private final String message;

    MemberCodeAndMessage(
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

    @Override
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
