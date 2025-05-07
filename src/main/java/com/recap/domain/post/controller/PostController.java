package com.recap.domain.post.controller;

import com.recap.domain.post.dto.PostRequest;
import com.recap.domain.post.entity.Post;
import com.recap.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin(value = "http://localhost:5173")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> postNewPost(@Valid @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.createPost(postRequest));
    }

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(){
        return ResponseEntity.ok(postService.fetchPosts());
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable long postId){
        postService.deletePost(postId);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> patchPost(@PathVariable long postId, @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.updatePost(postId, postRequest));
    }
    // TODO: 게시글 신고
    // TODO: 게시글 좋아요
    // TODO: 게시글 스크랩

}
