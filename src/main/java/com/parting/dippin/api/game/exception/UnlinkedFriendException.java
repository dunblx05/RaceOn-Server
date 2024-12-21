package com.parting.dippin.api.game.exception;

/**
 * statusCode - 403
 */
public class UnlinkedFriendException extends RuntimeException {

    public UnlinkedFriendException(String message) {
        super(message);
    }

    public UnlinkedFriendException() {
        super("친구 추가를 먼저 해주세요.");
    }
}
