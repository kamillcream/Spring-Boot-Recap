package com.recap.domain.post.dto;

import com.recap.domain.post.entity.Post;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PostRequest (
    @NotNull @Size(max = 90) String title,
    @NotNull String content
){
    public Post to(){
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
// dummy change
// dummy change
