package com.parting.dippin.api.auth.exception;

import com.parting.dippin.core.exception.CodeAndMessage;
import com.parting.dippin.core.exception.CommonException;

/**
 * 공통 응답에 대한 예외 코드들을 관리하는 Enum으로 CommonException 에서 활용된다. 응답 도메인 코드: CO
 *
 * @author 정민욱
 * @see CommonException
 */
public enum AuthenticationCodeAndMessage implements CodeAndMessage {

    USER_NOT_REGISTERED(false, 401, "AUAU01", "회원가입이 필요합니다.");

    private final boolean success;
    private final int httpStatusCode;
    private final String code;
    private final String message;

    AuthenticationCodeAndMessage(
            final boolean success,
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
    public String code() {
        return code;
    }

    @Override
    public int httpStatusCode() {
        return httpStatusCode;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }
}