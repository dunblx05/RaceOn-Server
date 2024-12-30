package com.parting.dippin.core.exception;

/**
 * 에러 응답의 내용물을 가지고 있는 interface로 이를
 * 각 도메인의 CodeAndMessage나 CommonCodeAndMessage가 상속받는다.
 *
 * @author 정민욱
 */
public interface CodeAndMessage {

    boolean isSuccess();

    String name();
    String code();

    int httpStatusCode();

    String message();
}
