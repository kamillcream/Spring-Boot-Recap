package com.recap.domain.user.controller;

import com.recap.domain.user.dto.request.UserRegisterRequest;
import com.recap.domain.user.entity.User;
import com.recap.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // TODO : 회원 가입
    @PostMapping
    public ResponseEntity<User> postNewUser(@RequestBody UserRegisterRequest userRegisterRequest){
        return ResponseEntity.ok(userService.register(userRegisterRequest));
    }
    // TODO : 회원 탈퇴
}
// dummy change
