package com.parting.dippin.api.game.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parting.dippin.core.base.BaseSocketData;
import com.parting.dippin.domain.game.dto.socket.GameStartResDto;
import java.util.NoSuchElementException;

public interface GameSocketService {

    public BaseSocketData invoke(int gameId, int memberId, String data) throws Exception;
}
