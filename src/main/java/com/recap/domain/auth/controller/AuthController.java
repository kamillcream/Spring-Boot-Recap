package com.recap.domain.auth.controller;

import com.recap.global.dto.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    // TODO : 로그인 구현, 단 로직은 별도로 구현하지 않고 스프링 시큐리티에 위임하고 컨트롤러는 요청만 받기.
    // TODO : 리프레쉬 토큰 재발급.
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
        return;
    }
}
// dummy change
// dummy change
