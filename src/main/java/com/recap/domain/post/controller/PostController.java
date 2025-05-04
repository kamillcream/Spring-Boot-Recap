package com.recap.domain.post.controller;

import com.recap.domain.post.dto.PostRequest;
import com.recap.domain.post.entity.Post;
import com.recap.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> postNewPost(@Valid @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.createPost(postRequest));
    }
    // TODO: 게시글 삭제
    // TODO: 게시글 수정
    // TODO: 게시글 신고
    // TODO: 게시글 좋아요
    // TODO: 게시글 스크랩

}
