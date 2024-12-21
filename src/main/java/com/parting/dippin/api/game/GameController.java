package com.parting.dippin.api.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parting.dippin.api.game.dto.GameGeneratedInfoDto;
import com.parting.dippin.api.game.dto.PostGameReqDto;
import com.parting.dippin.api.game.dto.PostGameResDto;
import com.parting.dippin.api.game.service.GameMessageService;
import com.parting.dippin.api.game.service.GameService;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/games")
@RestController()
public class GameController {

    private final GameService gameService;
    private final GameMessageService gameMessageService;
    private final ObjectMapper objectMapper;

    @PostMapping()
    public BaseResponse<PostGameResDto> requestGame(
        @LoggedInMemberId Integer memberId,
        @RequestBody PostGameReqDto postGameReqDto
    ) {
        GameGeneratedInfoDto gameInfo = this.gameService.requestGame(memberId, postGameReqDto);

        this.gameMessageService.sendInvitationMessage(gameInfo);

        PostGameResDto resDto = new PostGameResDto(gameInfo);

        return BaseResponse.success(resDto);
    }
}
