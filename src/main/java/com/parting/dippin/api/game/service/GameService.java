package com.parting.dippin.api.game.service;

import com.parting.dippin.api.game.dto.GameGeneratedInfoDto;
import com.parting.dippin.api.game.dto.PostGameReqDto;
import com.parting.dippin.api.game.exception.UnlinkedFriendException;
import com.parting.dippin.domain.friend.service.FriendValidationService;
import com.parting.dippin.domain.game.GameRegister;
import com.parting.dippin.domain.game.service.GameGeneratorService;
import com.parting.dippin.domain.game.service.GameValidationService;
import com.parting.dippin.domain.member.service.MemberReader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameService {

    private final MemberReader memberReader;
    private final FriendValidationService friendValidationService;
    private final GameValidationService gameValidationService;
    private final GameGeneratorService gameGeneratorService;

    @Transactional
    public GameGeneratedInfoDto requestGame(int memberId, PostGameReqDto postGameReqDto) {

        GameRegister gameRegister = new GameRegister(memberId);

        return gameRegister.register(
            gameValidationService,
            friendValidationService,
            memberReader,
            gameGeneratorService,
            postGameReqDto
        );
    }
}
