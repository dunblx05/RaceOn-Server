package com.parting.dippin.api.game.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.api.game.dto.GameGeneratedInfoDto;
import com.parting.dippin.core.common.fcm.FcmCommand;
import com.parting.dippin.core.common.fcm.FcmMessenger;
import com.parting.dippin.domain.token.service.TokenReader;
import java.util.List;
import java.util.Map;
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

            Map<String, String> putData = this.objectMapper.convertValue(
                gameGeneratedInfoDto,
                objectMapper.getTypeFactory()
                    .constructMapType(Map.class, String.class, String.class)
            );

            putData.put("command", FcmCommand.INVITE_GAME.getCommand());

            this.fcmMessenger.sendMessage(
                tokens,
                "경쟁 초대",
                gameGeneratedInfoDto.getRequestNickname() + " 님이 경쟁에 초대했어요.",
                putData
            );
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
    }
}
