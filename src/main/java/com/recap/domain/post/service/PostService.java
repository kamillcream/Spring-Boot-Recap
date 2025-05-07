package com.recap.domain.post.service;

import com.recap.domain.post.dto.PostRequest;
import com.recap.domain.post.entity.Post;
import com.recap.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(PostRequest postRequest){
        return postRepository.save(postRequest.to());
    }

    public List<Post> fetchPosts(){
        return postRepository.findAll();
    }

    public void deletePost(long id){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
    }

    @Transactional
    public Post updatePost(long postId, PostRequest postRequest){
        Post post = postRepository.findById(postId).orElseThrow();
        if (!postRequest.title().isEmpty()){
            post.setTitle(postRequest.title());
        }
        if (!postRequest.content().isEmpty()){
            post.setContent(postRequest.content());
        }
        return post;
    }
    // TODO: 게시글 신고
    // TODO: 게시글 좋아요
    // TODO: 게시글 스크랩
}
