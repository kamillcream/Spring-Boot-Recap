package com.recap.domain.comment.entity;

import com.recap.domain.post.entity.Post;
import com.recap.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 255, nullable = false)
    private String content;

    @Column(name = "created_dt", nullable = false, updatable = false)
    private LocalDateTime createdDt;

    @Column(nullable = false)
    private boolean deleted;

    @Column(name = "like_num", nullable = false)
    private int like;

    @PrePersist
    protected void onCreate() {
        this.createdDt = LocalDateTime.now();
        this.like = 0;
        this.deleted = false;
    }

    public void delete() {
        this.deleted = true;
    }

    public void increaseLike() {
        this.like++;
    }

    public void decreaseLike() {
        if (this.like > 0) this.like--;
    }

}