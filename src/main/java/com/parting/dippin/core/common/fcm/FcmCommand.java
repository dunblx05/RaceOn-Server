package com.parting.dippin.core.common.fcm;

import lombok.Getter;

@Getter
public enum FcmCommand {

    INVITE_GAME("INVITE_GAME");

    private final String command;

    FcmCommand(String command) {
        this.command = command;
    }
}
