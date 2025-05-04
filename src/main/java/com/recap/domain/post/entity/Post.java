package com.recap.domain.post.entity;

import com.recap.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(nullable = false, length = 90)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_dt", nullable = false, updatable = false)
    private LocalDateTime createdDt;

    @Column(name = "modified_dt")
    private LocalDateTime modifiedDt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "like_num")
    private Integer likeNum;

    private Integer scrap;

    @PrePersist
    public void preCreate() {
        this.createdDt = LocalDateTime.now();
        this.likeNum = 0;
        this.scrap = 0;
        this.isDeleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDt = LocalDateTime.now();
    }

}
