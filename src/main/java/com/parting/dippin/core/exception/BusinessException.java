package com.parting.dippin.core.exception;

/**
 * Spring이 내려주는 Exception이 아닌 Business Logic에서 발생한 예외들을
 * 하나로 묶기위한 부모 클래스이다.
 *
 * @see com.parting.dippin.core.exception.GlobalExceptionHandler#resolveBusinessException
 * @author 정민욱
 */
public class BusinessException extends RuntimeException {

    private final CodeAndMessage codeAndMessage;

    public BusinessException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage.message());
        this.codeAndMessage = codeAndMessage;
    }

    public CodeAndMessage getCodeAndMessage() {
        return codeAndMessage;
    }

    public int getHttpStatusCode() {
        return codeAndMessage.httpStatusCode();
    }
}