package com.parting.dippin.domain.token.service;

import com.parting.dippin.entity.token.repository.FcmTokenRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenReader {
    private final FcmTokenRepository fcmTokenRepository;

    public List<String> getTokens(int memberId) {
        return this.fcmTokenRepository.findTokensByMemberId(memberId);
    }
}
