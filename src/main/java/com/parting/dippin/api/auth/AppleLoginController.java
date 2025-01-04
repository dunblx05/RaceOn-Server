package com.parting.dippin.api.auth;

import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.api.auth.service.OauthService;
import com.parting.dippin.core.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppleLoginController {

    private final OauthService appleOauthService;

    @GetMapping("/apple/callback")
    public BaseResponse<GetJwtResDto> appleCallback(
            @RequestParam(value = "code", required = false) String code
    ) {
        GetJwtResDto jwtResDto = appleOauthService.callBack(code);

        return BaseResponse.ok(jwtResDto);
    }
}
