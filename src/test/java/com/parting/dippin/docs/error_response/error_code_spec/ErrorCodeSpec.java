package com.parting.dippin.docs.error_response.error_code_spec;

import com.parting.dippin.core.exception.CommonCodeAndMessage;

/**
 * CommonErrorResponseDocsTest 에 사용되는 클래스로
 *  에러 응답을 문서화 하는데 사용되는 클래스이다.
 *
 * @author 정민욱
 *
 */
public class ErrorCodeSpec {

    private boolean success;
    private int httpStatusCode;
    private String code;
    private String message;

    public ErrorCodeSpec(CommonCodeAndMessage codeAndMessage) {
        this.success = codeAndMessage.isSuccess();
        this.httpStatusCode = codeAndMessage.httpStatusCode();
        this.code = codeAndMessage.code();
        this.message = codeAndMessage.message();
    }

    public boolean isSuccess() {
        return success;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
