package com.parting.dippin.core.exception;

/**
 * 공통 응답에 대한 예외 코드들을 관리하는 Enum으로 CommonException 에서 활용된다.
 * 응답 도메인 코드: CO
 *
 * @author 정민욱
 * @see CommonException
 */
public enum CommonCodeAndMessage implements CodeAndMessage {
    OK(true, 200, "COOK01", "요청에 성공했습니다."),
    CREATED(true, 201, "COOK02", "생성에 성공했습니다."),
    BAD_REQUEST(false, 400, "COBR01", "올바른 파라미터를 입력해주세요"),
    INVALID_URL(false, 404, "COBR02", "올바르지 않은 URL 입니다."),
    REQUIRED_LOGIN(false, 401, "COAE01", "로그인이 필요합니다."),
    INVALID_USER_ID(false, 403, "COAO01", "올바른 사용자 아이디가 아닙니다."),
    UN_AUTHORIZED(false, 403, "COAO02", "권한이 존재하지 않습니다."),
    INVALID_USER_TOKEN(false, 403, "COAO03", "유효하지 않는 사용자 토큰입니다."),
    INTERNAL_SERVER_ERROR(false, 500, "COIS01", "서버 내부 오류입니다."),
    DATA_ACCESS_EXCEPTION(false, 500, "COES01", "데이터베이스와 통신하는 과정에서 오류가 발생했습니다"),
    COMMON_UN_RESOLVED_EXCEPTION(false, 500, "COET01", "정의되지 않은 공통 오류입니다.");

    private final boolean success;
    private final int httpStatusCode;
    private final String code;
    private final String message;

    CommonCodeAndMessage(
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