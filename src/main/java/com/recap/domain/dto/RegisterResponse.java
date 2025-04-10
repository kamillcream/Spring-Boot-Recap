package com.recap.domain.dto;

import com.recap.domain.entity.MySQLUser;
import lombok.Builder;

@Builder
public record RegisterResponse(
        String name,
        String userId
) {
    public static RegisterResponse from(MySQLUser user){
        return RegisterResponse.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .build();
    }
}
