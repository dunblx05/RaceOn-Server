package com.parting.dippin.domain.token.service;

import com.parting.dippin.entity.token.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenDeleteService {

    private final FcmTokenRepository fcmTokenRepository;

    public void delete(String token) {
        fcmTokenRepository.deleteByToken(token);
    }
}
