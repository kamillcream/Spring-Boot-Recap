package com.recap.domain.dto;

import lombok.Builder;

@Builder
public record RegisterRequest (
        String type,
        String userId,
        String name,
        String password
){
}
