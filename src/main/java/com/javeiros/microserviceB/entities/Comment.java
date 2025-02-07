package com.javeiros.microserviceB.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor @AllArgsConstructor
public class Comment {

    @Id
    String id;
    String postId;
    String name;
    String email;
    String body;
}
