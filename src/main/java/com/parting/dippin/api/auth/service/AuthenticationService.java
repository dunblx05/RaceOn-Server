package com.parting.dippin.api.auth.service;

import com.parting.dippin.api.auth.dto.GetJwtResDto;
import com.parting.dippin.core.common.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenProvider tokenProvider;

    public GetJwtResDto login(long memberId) {
        return tokenProvider.createJwt(memberId);
    }

    public GetJwtResDto reissue(long memberId) {
        return tokenProvider.createJwt(memberId);
    }
}
