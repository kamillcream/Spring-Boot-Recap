package com.recap.domain.user.entity;

import com.recap.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20, nullable = false)
    private String userId;

    @Column(name = "password", nullable = false) // length default = 255, 별도 명시 x
    private String password;

    @Column(length = 25, nullable = false)
    private String university;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50)
    private String nickname;
}
