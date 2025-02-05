package com.javeiros.microserviceB.entities.dto;


import com.javeiros.microserviceB.entities.Post;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PostResponseDTO {
    String id;
    String title;
    String body;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.body = post.getBody();
    }


}
