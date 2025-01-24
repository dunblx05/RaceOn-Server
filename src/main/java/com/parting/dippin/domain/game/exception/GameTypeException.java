package com.parting.dippin.domain.game.exception;

import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.ALREADY_FINISHED_GAME;
import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.ALREADY_MATCHING_MEMBER;
import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.ALREADY_ONGOING_GAME;
import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.ALREADY_PARTICIPANT_MEMBER;
import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.GAME_NOT_FOUND;
import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.NOT_AVAILABLE_GAME;
import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.NOT_COMPLETED_GAME;
import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.NOT_GAME_MEMBER;
import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.NOT_MATCHING_GAME;
import static com.parting.dippin.domain.game.exception.GameCodeAndMessage.NOT_ONGOING_GAME;

import com.parting.dippin.core.exception.BusinessException;
import com.parting.dippin.core.exception.CodeAndMessage;
import java.util.HashMap;
import java.util.Map;

public sealed class GameTypeException extends BusinessException {

    private static final Map<GameCodeAndMessage, GameTypeException> exceptionMap = new HashMap<>();

    private GameTypeException(final CodeAndMessage codeAndMessage) {
        super(codeAndMessage);
    }

    static {
        exceptionMap.put(ALREADY_PARTICIPANT_MEMBER, new AlreadyParticipantMemberException());
        exceptionMap.put(NOT_GAME_MEMBER, new NotGameMemberException());
        exceptionMap.put(NOT_AVAILABLE_GAME, new NotAvailableGameException());
        exceptionMap.put(ALREADY_ONGOING_GAME, new AlreadyOngoingGameException());
        exceptionMap.put(ALREADY_FINISHED_GAME, new AlreadyFinishedGameException());
        exceptionMap.put(ALREADY_MATCHING_MEMBER, new AlreadyMatchingMemberException());
        exceptionMap.put(GAME_NOT_FOUND, new GameNotFoundException());
        exceptionMap.put(NOT_COMPLETED_GAME, new NotCompletedGameException());
        exceptionMap.put(NOT_ONGOING_GAME, new NotOngoingGameException());
        exceptionMap.put(NOT_MATCHING_GAME, new NotMatchingGameException());
    }

    public static GameTypeException from(final GameCodeAndMessage codeAndMessage) {
        GameTypeException exception = exceptionMap.get(codeAndMessage);

        return exception;
    }

    private static final class AlreadyParticipantMemberException extends GameTypeException {

        public AlreadyParticipantMemberException() {
            super(ALREADY_PARTICIPANT_MEMBER);
        }
    }

    private static final class NotGameMemberException extends GameTypeException {

        public NotGameMemberException() {
            super(NOT_GAME_MEMBER);
        }
    }

    private static final class NotAvailableGameException extends GameTypeException {

        public NotAvailableGameException() {
            super(NOT_AVAILABLE_GAME);
        }
    }

    private static final class AlreadyOngoingGameException extends GameTypeException {

        public AlreadyOngoingGameException() {
            super(ALREADY_ONGOING_GAME);
        }
    }

    private static final class AlreadyFinishedGameException extends GameTypeException {

        public AlreadyFinishedGameException() {
            super(ALREADY_FINISHED_GAME);
        }
    }

    private static final class AlreadyMatchingMemberException extends GameTypeException {

        public AlreadyMatchingMemberException() {
            super(ALREADY_MATCHING_MEMBER);
        }
    }

    private static final class GameNotFoundException extends GameTypeException {

        public GameNotFoundException() {
            super(GAME_NOT_FOUND);
        }
    }

    private static final class NotCompletedGameException extends GameTypeException {

        public NotCompletedGameException() {
            super(NOT_COMPLETED_GAME);
        }
    }

    private static final class NotOngoingGameException extends GameTypeException {

        public NotOngoingGameException() {
            super(NOT_ONGOING_GAME);
        }
    }

    private static final class NotMatchingGameException extends GameTypeException {
        public NotMatchingGameException() {
            super(NOT_MATCHING_GAME);
        }
    }
}
