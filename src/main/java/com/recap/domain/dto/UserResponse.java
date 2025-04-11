package com.recap.domain.dto;

import com.recap.domain.entity.MySQLUser;
import lombok.Builder;

@Builder
public record UserResponse(
        String name,
        String userId
) {
    public static UserResponse from(MySQLUser user){
        return UserResponse.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .build();
    }
}
