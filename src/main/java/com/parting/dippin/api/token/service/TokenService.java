package com.parting.dippin.api.token.service;

import com.parting.dippin.domain.token.service.TokenRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenRegisterService tokenRegisterService;

    public void saveToken(int memberId, String token) {
        this.tokenRegisterService.register(memberId, token);
    }
}
