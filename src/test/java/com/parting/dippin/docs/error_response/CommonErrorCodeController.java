package com.parting.dippin.docs.error_response;

import com.parting.dippin.core.exception.CommonCodeAndMessage;
import com.parting.dippin.docs.error_response.error_code_spec.ErrorCodeSpec;
import com.parting.dippin.docs.error_response.error_code_spec.ErrorCodeSpecs;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공통 에러 코드를 문서화하는 용도의 Controller이다.
 *
 * @author 정민욱
 * @see com.parting.dippin.docs.CommonErrorResponseDocsTest
 */
@RestController
@RequestMapping("/api/specs/common-error-response")
public class CommonErrorCodeController {

    @GetMapping
    public ErrorCodeSpecs getErrorCodes() {
        List<CommonCodeAndMessage> codeAndMessages =
                Arrays.stream(CommonCodeAndMessage.values())
                        .toList();

        Map<String, ErrorCodeSpec> errorCodes = new HashMap<>();

        for (CommonCodeAndMessage codeAndMessage : codeAndMessages) {
            errorCodes.put(codeAndMessage.name(), new ErrorCodeSpec(codeAndMessage));
        }
        return new ErrorCodeSpecs(errorCodes);
    }
}
