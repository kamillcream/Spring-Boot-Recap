package com.recap.domain.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MongoUser {
    @Id
    private String id;
    private String name;
    private String userId;
    private String password;
}
