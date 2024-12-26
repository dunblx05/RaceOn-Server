package com.parting.dippin.core.base;

import lombok.Getter;

@Getter
public class BaseSocketRequest {
    String command;
    String data;

    @Override
    public String toString() {
        return "{'command':'" + this.command + ", 'data':'" + this.data + "'}";
    }
}
