package com.parting.dippin.api.game.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parting.dippin.api.game.service.GameSocketService;
import com.parting.dippin.core.base.BaseSocketData;
import com.parting.dippin.domain.game.GameInvitationRejecter;
import com.parting.dippin.domain.game.service.GameReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameInvitationRejectService implements GameSocketService {

    private final GameReader gameReader;

    @Override
    @Transactional
    public BaseSocketData invoke(int gameId, int memberId, String data) throws JsonProcessingException {
        GameInvitationRejecter gameInvitationRejecter = new GameInvitationRejecter(gameId, memberId);

        return gameInvitationRejecter.reject(gameReader);
    }
}
