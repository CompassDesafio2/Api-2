package com.javeiros.microserviceB.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post implements Serializable {

    @Id
    @MongoId
    private String id;
    @NotEmpty
    @NotNull
    private String userId;
    @NotEmpty
    @NotNull
    private String title;

    private String body;


}
