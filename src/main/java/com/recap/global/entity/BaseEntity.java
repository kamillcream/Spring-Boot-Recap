package com.recap.global.entity;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class BaseEntity {
    @Column(name = "created_dt", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "modified_dt")
    private LocalDateTime modifiedDt;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
// dummy change
