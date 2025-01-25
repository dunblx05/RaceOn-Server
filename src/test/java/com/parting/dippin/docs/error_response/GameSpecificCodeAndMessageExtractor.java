package com.parting.dippin.docs.error_response;

import com.parting.dippin.core.exception.CodeAndMessage;
import com.parting.dippin.domain.game.exception.GameCodeAndMessage;
import com.parting.dippin.domain.game.exception.GameTypeException;
import java.util.Arrays;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public class GameSpecificCodeAndMessageExtractor {

    public static <I> CodeAndMessage[] extractGameStartCodeAndMessage(
            GameCodeAndMessage[] values,
            Class<I> exceptionType
    ) {
        return Arrays.stream(values)
                .filter(isGameStartException(exceptionType))
                .toArray(CodeAndMessage[]::new);
    }

    @NotNull
    private static <I> Predicate<GameCodeAndMessage> isGameStartException(Class<I> exceptionType) {
        return codeAndMessage -> {
            GameTypeException gameException = GameTypeException.from(codeAndMessage);
            return exceptionType.isInstance(gameException);
        };
    }
}
