package com.recap.domain.post.entity;

import com.recap.domain.post.dto.PostRequest;
import com.recap.domain.university.entity.University;
import com.recap.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicUpdate
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "univ_id")
//    private University university;

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
    private Integer like;

    private Integer scrap;

    @PrePersist
    public void preCreate() {
        this.createdDt = LocalDateTime.now();
        this.like = 0;
        this.scrap = 0;
        this.isDeleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDt = LocalDateTime.now();
    }

    public void increaseScrap(){
        this.scrap++;
    }

    public void decreaseScrap(){
        if(this.scrap > 0){
            this.scrap--;
        }
    }

    public void increaseLike(){
        this.like++;
    }

    public void decreaseLike(){
        if(this.like > 0){
            this.like--;
        }
    }


}
// dummy change
// dummy change
