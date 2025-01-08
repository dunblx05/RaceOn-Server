package com.parting.dippin.api.auth;

import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.api.auth.dto.PostLoginReqDto;
import com.parting.dippin.api.auth.service.AuthenticationService;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/temp/login")
    public BaseResponse<GetJwtResDto> login(
            @RequestParam("memberId") int memberId
    ) {
        GetJwtResDto jwtDto = authService.login(memberId);

        return BaseResponse.ok(jwtDto);
    }

    @PostMapping("/reissue")
    public BaseResponse<GetJwtResDto> reissue(
            @LoggedInMemberId Integer memberId
    ) {
        GetJwtResDto jwtDto = authService.reissue(memberId);

        return BaseResponse.ok(jwtDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login")
    public BaseResponse<GetJwtResDto> login(
            @RequestBody PostLoginReqDto postLoginReqDto
    ) {
        GetJwtResDto jwtDto = authService.login(postLoginReqDto);

        return BaseResponse.created(jwtDto);
    }
}
