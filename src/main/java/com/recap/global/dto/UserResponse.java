package com.recap.global.dto;

import com.recap.global.entity.User;
import lombok.Builder;

@Builder
public record UserResponse(
        String name,
        String userId
) {
    public static UserResponse from(User user){
        return UserResponse.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .build();
    }
}
