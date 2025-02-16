package com.parting.dippin.core.base;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseSocketRequest<T> {
    String command;
    T data;
}
