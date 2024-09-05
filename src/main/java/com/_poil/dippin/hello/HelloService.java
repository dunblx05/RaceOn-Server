package com._poil.dippin.hello;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String hello() {
        return "Hello World";
    }
}
