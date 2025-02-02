package com.parting.dippin.api.game;

import com.parting.dippin.api.game.service.GameAllTerminateService;
import com.parting.dippin.core.base.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *   클라이언트 개발자분들의 요청으로 만들어지는 API입니다.
 *   테스트 편의성을 위해 만들어지는 코드로 개발 서버에만 올라갑니다.
 *
 */
@Profile("dev")
@RequiredArgsConstructor
@RequestMapping(value = "/games")
@RestController
public class GameTerminationTestController {

    private final GameAllTerminateService gameAllTerminateService;

    @DeleteMapping()
    public BaseResponse<Void> terminateAll() {
        gameAllTerminateService.terminateAll();

        return BaseResponse.ok();
    }
}
