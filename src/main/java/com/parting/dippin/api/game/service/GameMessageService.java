package com.parting.dippin.api.game.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.api.game.dto.GameGeneratedInfoDto;
import com.parting.dippin.core.common.fcm.FcmMessenger;
import com.parting.dippin.domain.token.service.TokenReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameMessageService {

    private final FcmMessenger fcmMessenger;
    private final TokenReader tokenReader;
    private final ObjectMapper objectMapper;

    @Async
    public void sendInvitationMessage(GameGeneratedInfoDto gameGeneratedInfoDto) {
        try {
            List<String> tokens = this.tokenReader.getTokens(
                gameGeneratedInfoDto.getReceivedMemberId());

            this.fcmMessenger.sendMessage(tokens, "게임 초대",
                this.objectMapper.writeValueAsString(gameGeneratedInfoDto), "");
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }
}
