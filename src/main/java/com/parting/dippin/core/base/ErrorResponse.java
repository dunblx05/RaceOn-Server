package com.parting.dippin.core.base;

import com.parting.dippin.core.exception.CodeAndMessage;

public class ErrorResponse {

    private boolean isSuccess;
    private String code;
    private String message;

    private ErrorResponse() {
    }

    private ErrorResponse(final CodeAndMessage codeAndMessage) {
        this.isSuccess = codeAndMessage.isSuccess();
        this.code = codeAndMessage.code();
        this.message = codeAndMessage.message();
    }

    public static ErrorResponse from(final CodeAndMessage codeAndMessage) {
        return new ErrorResponse(codeAndMessage);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
