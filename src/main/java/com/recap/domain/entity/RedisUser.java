package com.recap.domain.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedisUser {
    @Id
    private String id;
    private String name;
    private String userId;
    private String password;
}
