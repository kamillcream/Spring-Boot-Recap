package com.recap.domain.user.dto.request;

public record UserRegisterRequest(
        String userId,
        String password,
        String name,
        String nickname,
        String univCode
){
}
