package com.recap.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {
    @GetMapping
    public String test(){
        return "Hello World!";
    }
}
// dummy change
// dummy change
