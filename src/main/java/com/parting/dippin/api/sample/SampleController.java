package com.parting.dippin.api.sample;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SampleController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String sample(String message) {
        return "Received: " + message;
    }
}
