package com.recap.domain.post.service;

import com.recap.domain.post.dto.PostRequest;
import com.recap.domain.post.entity.Post;
import com.recap.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(PostRequest postRequest){
        return postRepository.save(postRequest.to());
    }
    // TODO: 게시글 삭제
    // TODO: 게시글 수정
    // TODO: 게시글 신고
    // TODO: 게시글 좋아요
    // TODO: 게시글 스크랩
}
