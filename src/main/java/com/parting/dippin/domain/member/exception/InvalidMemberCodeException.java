package com.parting.dippin.domain.member.exception;

public class InvalidMemberCodeException extends RuntimeException{

    public InvalidMemberCodeException(String message) {
        super(message);
    }

    public InvalidMemberCodeException() {
        super("유효하지 않은 코드에요.");
    }
}
