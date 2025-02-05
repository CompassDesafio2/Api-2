package com.javeiros.microserviceB.entities.dto;


import com.javeiros.microserviceB.entities.Post;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PostDTO {
    String id;
    String userId;
    String title;
    String body;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.body = post.getBody();
    }


}
