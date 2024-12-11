package com.parting.dippin.api.friend.exception;

/**
 * statusCode - 409
 */
public class AlreadyFriendException extends RuntimeException {

    public AlreadyFriendException(String message) {
        super(message);
    }

    public AlreadyFriendException() {
        super("이미 친구 추가한 상태에요.");
    }
}
