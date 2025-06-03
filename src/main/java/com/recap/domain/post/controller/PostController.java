package com.recap.domain.post.controller;

import com.recap.domain.post.dto.PostRequest;
import com.recap.domain.post.entity.Post;
import com.recap.domain.post.service.PostService;
import com.recap.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin(value = "http://localhost:5173")
public class PostController {

    private final PostService postService;

    @PostMapping("/{univCode}")
    public ResponseEntity<Post> postNewPost(@PathVariable String univCode,
                                            @Valid @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.createPost(postRequest));
    }

    @GetMapping("/{univCode}")
    public ResponseEntity<List<Post>> getPosts(@PathVariable String univCode){
        return ResponseEntity.ok(postService.fetchPosts());
    }

    @DeleteMapping("/{postId}")
    public void deletePost(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable long postId){
        postService.deletePost(postId);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> patchPost(@PathVariable long postId, @RequestBody PostRequest postRequest){
        return ResponseEntity.ok(postService.updatePost(postId, postRequest));
    }
    // TODO: 게시글 신고
//    @PostMapping("/{postId}/report")
//    public ResponseEntity<Report> createReport(@PathVariable long postId){
//        return ResponseEntity.ok(postService.reportPost(postId));
//    }

    @PatchMapping("/{postId}/like")
    public ResponseEntity<Post> patchLike(@PathVariable long postId,
                                          @RequestParam boolean isAlreadyLike){
        return ResponseEntity.ok(postService.updateLike(postId, isAlreadyLike));
    }

    @PatchMapping("/{postId}/scrap")
    public ResponseEntity<Post> patchScrap(@PathVariable long postId,
                                           @RequestParam boolean isAlreadyScrap){
        return ResponseEntity.ok(postService.updateScrap(postId, isAlreadyScrap));
    }
}
// dummy change
// dummy change
