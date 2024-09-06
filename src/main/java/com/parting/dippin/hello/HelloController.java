package com.parting.dippin.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("hello")
@RestController()
public class HelloController {

    private final HelloService helloService;

    @GetMapping()
    public String hello() {
        return this.helloService.hello();
    }
}
