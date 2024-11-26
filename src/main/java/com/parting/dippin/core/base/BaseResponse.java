package com.parting.dippin.core.base;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {

    boolean isSuccess;
    int code;
    T data;

    private BaseResponse(boolean isSuccess, int code, T data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.data = data;
    }

    public BaseResponse(boolean isSuccess, int code) {
        this.isSuccess = isSuccess;
        this.code = code;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, 200, data);
    }

    public static BaseResponse<Void> success() {
        return new BaseResponse<>(true, 200);
    }

    public static BaseResponse<Void> fail(int code) {
        return new BaseResponse<>(true, code);
    }
}
