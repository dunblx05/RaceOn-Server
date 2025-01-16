package com.parting.dippin.api.game;

import com.parting.dippin.api.game.dto.GameGeneratedInfoDto;
import com.parting.dippin.api.game.dto.PostGameReqDto;
import com.parting.dippin.api.game.dto.PostGameResDto;
import com.parting.dippin.api.game.service.GameMessageService;
import com.parting.dippin.api.game.service.GameService;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import com.parting.dippin.core.exception.CommonCodeAndMessage;
import com.parting.dippin.core.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/games")
@RestController()
public class GameController {

    private final GameService gameService;
    private final GameMessageService gameMessageService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public BaseResponse<PostGameResDto> requestGame(
            @LoggedInMemberId Integer memberId,
            @RequestBody PostGameReqDto postGameReqDto
    ) {
        if (memberId == postGameReqDto.getFriendId()) {
            throw CommonException.from(CommonCodeAndMessage.BAD_REQUEST);
        }

        GameGeneratedInfoDto gameInfo = this.gameService.requestGame(memberId, postGameReqDto);

        this.gameMessageService.sendInvitationMessage(gameInfo);

        PostGameResDto resDto = new PostGameResDto(gameInfo);

        return BaseResponse.created(resDto);
    }
}
