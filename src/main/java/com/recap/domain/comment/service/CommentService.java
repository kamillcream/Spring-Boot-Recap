package com.recap.domain.comment.service;

import com.recap.domain.comment.entity.Comment;
import com.recap.domain.comment.repository.CommentRepository;
import com.recap.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment createComment(long postId, String content){
        Comment comment = Comment.builder()
                .post(postRepository.findById(postId).orElseThrow())
                .content(content)
                .build();
        // TODO: 토큰으로 정보 받아와 유저 포함
        return commentRepository.save(comment);
    }

    @Transactional
    public int likeComment(long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.increaseLike();
        return comment.getLike();
    }

    @Transactional
    public int deleteComment(long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.delete();
        return comment.getLike();
    }
}
// dummy change
