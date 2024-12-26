package com.parting.dippin.core.base;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseSocketResponse<T> {

    String command;
    int code;
    T data;

    private BaseSocketResponse(String command, T data, int code) {
        this.command = command;
        this.code = code;
        this.data = data;
    }

    public static <T> BaseSocketResponse<T> success(String command, T data) {
        return new BaseSocketResponse<>(command, data, 200);
    }

    public static <T> BaseSocketResponse<T> failure(String command) {
        return new BaseSocketResponse<>(command, null, 500);
    }

    public static <T> BaseSocketResponse<T> notFound(String command) {
        return new BaseSocketResponse<>(command, null, 404);
    }

    public static <T> BaseSocketResponse<T> baseRequest(String command) {
        return new BaseSocketResponse<>(command, null, 400);
    }
}
