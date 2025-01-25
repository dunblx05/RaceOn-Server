package com.parting.dippin.docs.error_response;

import static com.parting.dippin.docs.error_response.GameSpecificCodeAndMessageExtractor.extractGameStartCodeAndMessage;

import com.parting.dippin.core.exception.CodeAndMessage;
import com.parting.dippin.core.exception.CommonCodeAndMessage;
import com.parting.dippin.docs.error_response.error_code_spec.ErrorCodeSpec;
import com.parting.dippin.docs.error_response.error_code_spec.ErrorCodeSpecs;
import com.parting.dippin.domain.game.exception.GameCodeAndMessage;
import com.parting.dippin.domain.game.exception.docs.GameStartException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specs/game-start-error-response")
public class GameStartErrorCodeController {

    @GetMapping
    public ErrorCodeSpecs getErrorCodes() {
        List<CodeAndMessage> gameStartCodeAndMessages
                = new java.util.ArrayList<>(Arrays.stream(
                        extractGameStartCodeAndMessage(GameCodeAndMessage.values(), GameStartException.class))
                .toList()
        );
        gameStartCodeAndMessages.add(CommonCodeAndMessage.BAD_REQUEST);

        Map<String, ErrorCodeSpec> errorCodes = new HashMap<>();

        for (CodeAndMessage codeAndMessage : gameStartCodeAndMessages) {
            errorCodes.put(codeAndMessage.name(), new ErrorCodeSpec(codeAndMessage));
        }
        return new ErrorCodeSpecs(errorCodes);
    }
}
