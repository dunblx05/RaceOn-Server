package com.parting.dippin.domain.game.exception;

public class InvalidGameEndException extends Exception {

    public InvalidGameEndException() {
        super("이미 게임이 종료 되었어요.");
    }
}