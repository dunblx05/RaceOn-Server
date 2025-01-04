package com.parting.dippin.domain.report.exception;

import com.parting.dippin.core.exception.CodeAndMessage;

// 도메인 코드 : ME
public enum ReportCodeAndMessage implements CodeAndMessage {
    INVALID_REPORTED_MEMBER_ID(false, 400, "RPBR01", "존재하지 않는 유저입니다.");

    private final boolean success;
    private final int httpStatusCode;
    private final String code;
    private final String message;

    ReportCodeAndMessage(
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
