package com.parting.dippin.api.auth;

import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.api.auth.service.OauthService;
import com.parting.dippin.core.base.BaseResponse;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final OauthService kakaoOauthService;

    @GetMapping("/kakao/callback")
    public BaseResponse<GetJwtResDto> callBack(
            @RequestParam @Nullable String code,
            @RequestParam(name = "open_id") @Nullable String openId
    ) {
        if (code == null) {
            throw new RuntimeException();
        }

        GetJwtResDto jwtResDto = kakaoOauthService.callBack(code);

        return BaseResponse.success(jwtResDto);
    }
}
