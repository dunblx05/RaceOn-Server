package com.parting.dippin.core.base;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.CREATED;
import static com.parting.dippin.core.exception.CommonCodeAndMessage.OK;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.parting.dippin.core.exception.CodeAndMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {

    boolean isSuccess;
    String code;
    String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    private BaseResponse(CodeAndMessage codeAndMessage, T data) {
        this.isSuccess = codeAndMessage.isSuccess();
        this.code = codeAndMessage.code();
        this.message = codeAndMessage.message();
        this.data = data;
    }

    private BaseResponse(CodeAndMessage codeAndMessage) {
        this.isSuccess = true;
        this.code = codeAndMessage.code();
        this.message = codeAndMessage.message();
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(OK, data);
    }

    public static <T> BaseResponse<T> created(T data){
        return new BaseResponse<>(CREATED, data);
    }

    public static BaseResponse<Void> ok() {
        return new BaseResponse<>(OK);
    }

    public static BaseResponse<Void> created() {
        return new BaseResponse<>(CREATED);
    }
}
