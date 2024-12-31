package com.parting.dippin.api.token;

import com.parting.dippin.api.token.dto.PostTokenReqDto;
import com.parting.dippin.api.token.service.TokenService;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("tokens")
@RestController()
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("{memberId}")
    public BaseResponse<Void> saveToken(
        @LoggedInMemberId Integer memberId,
        @PathVariable("memberId") String pMemberId,
        @RequestBody PostTokenReqDto postTokenReqDto
    ) {
        tokenService.saveToken(memberId, postTokenReqDto.getToken());

        return BaseResponse.ok();
    }
}
