package com.javeiros.microserviceB.entities;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document
@Data
@NoArgsConstructor @AllArgsConstructor
public class Comment {

    @Id
    @MongoId
    String id;

    @NotNull(message = "TESTE")
    @NotEmpty
    String postId;

    @NotNull
    @NotEmpty
    String name;

    @NotNull
    @NotEmpty
    String email;

    String body;


}
