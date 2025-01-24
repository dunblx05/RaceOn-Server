package com.parting.dippin.api.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parting.dippin.core.base.BaseSocketData;

public interface GameSocketService {

    BaseSocketData invoke(int gameId, int memberId, String data)
        throws JsonProcessingException;
}
