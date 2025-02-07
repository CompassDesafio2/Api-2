package com.javeiros.microserviceB.entities.dto;

import com.javeiros.microserviceB.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CommentDTO {

    String id;
    String postId;
    String name;
    String email;
    String body;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.name = comment.getName();
        this.email = comment.getEmail();
        this.body = comment.getBody();

    }

}
