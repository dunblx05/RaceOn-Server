package com.parting.dippin.docs.error_response.error_code_spec;

import java.util.Map;

/**
 * CommonErrorResponseDocsTest 에서 사용되는 공통 에러 응답을 문서화 하는데
 * 사용되는 클래스이다.
 * ErrorCodeSpec 의 first class Collection 으로 이해하면 된다.
 *
 * @author 정민욱
 * @see com.parting.dippin.docs.CommonErrorResponseDocsTest
 * @see com.parting.dippin.docs.error_response.error_code_spec.ErrorCodeSpec
 */
public class ErrorCodeSpecs {

    private final Map<String, ErrorCodeSpec> errorCodes;

    public ErrorCodeSpecs(Map<String, ErrorCodeSpec> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public Map<String, ErrorCodeSpec> getErrorCodes() {
        return errorCodes;
    }
}
