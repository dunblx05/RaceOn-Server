package com.parting.dippin.domain.token.service;

import com.parting.dippin.entity.token.FcmTokenEntity;
import com.parting.dippin.entity.token.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenRegisterService {

    private final FcmTokenRepository fcmTokenRepository;

    public void register(int memberId, String token) {
        FcmTokenEntity fcmTokenEntity = this.fcmTokenRepository.findByToken(token).orElse(null);

        if (fcmTokenEntity != null && fcmTokenEntity.getMemberId() == memberId) {
            return;
        }

        if (fcmTokenEntity != null) {
            fcmTokenRepository.delete(fcmTokenEntity);
        }

        FcmTokenEntity newEntity = FcmTokenEntity.of(memberId, token);

        this.fcmTokenRepository.save(newEntity);
    }

}
