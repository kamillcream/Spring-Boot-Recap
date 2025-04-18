package com.recap.global.dto;

import lombok.Builder;

@Builder
public record RegisterRequest (
        String type,
        String userId,
        String name,
        String password
){
}
