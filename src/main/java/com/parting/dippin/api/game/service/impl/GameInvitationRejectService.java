package com.parting.dippin.api.game.service.impl;

import com.parting.dippin.core.base.BaseSocketData;
import com.parting.dippin.domain.game.GameInvitationRejecter;
import com.parting.dippin.domain.game.service.GameReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameInvitationRejectService{

    private final GameReader gameReader;

    @Transactional
    public BaseSocketData reject(int gameId, int memberId) {
        GameInvitationRejecter gameInvitationRejecter = new GameInvitationRejecter(gameId, memberId);

        return gameInvitationRejecter.reject(gameReader);
    }
}
