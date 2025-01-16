package com.parting.dippin.core.base;


import static com.parting.dippin.core.exception.CommonCodeAndMessage.CREATED;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.OK;

import com.parting.dippin.core.exception.CodeAndMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseSocketResponse<T> {

    boolean isSuccess;
    String command;
    int statusCode;
    String code;

    String message;
    T data;

    private BaseSocketResponse(CodeAndMessage codeAndMessage, String command, T data) {
        this.isSuccess = codeAndMessage.isSuccess();
        this.command = command;
        this.code = codeAndMessage.code();
        this.statusCode = codeAndMessage.httpStatusCode();
        this.message = codeAndMessage.message();
        this.data = data;
    }

    private BaseSocketResponse(CodeAndMessage codeAndMessage, String command) {
        this.isSuccess = codeAndMessage.isSuccess();
        this.command = command;
        this.code = codeAndMessage.code();
        this.statusCode = codeAndMessage.httpStatusCode();
        this.message = codeAndMessage.message();
    }

    public static <T> BaseSocketResponse<T> ok(String command, T data) {
        return new BaseSocketResponse<>(OK, command, data);
    }

    public static <T> BaseSocketResponse<T> ok(String command) {
        return new BaseSocketResponse<>(OK, command);
    }

    public static <T> BaseSocketResponse<T> created(String command, T data) {
        return new BaseSocketResponse<>(CREATED, command, data);
    }

    public static <T> BaseSocketResponse<T> created(String command) {
        return new BaseSocketResponse<>(CREATED, command);
    }

    public static <T> BaseSocketResponse<T> error(CodeAndMessage codeAndMessage, String command) {
        return new BaseSocketResponse<>(codeAndMessage, command);
    }
}
