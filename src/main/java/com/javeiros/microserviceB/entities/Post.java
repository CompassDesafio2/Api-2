package com.javeiros.microserviceB.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post implements Serializable {

    @Id
    private String id;
    private String userId;
    private String title;
    private String body;


    public Post(String number, String title, String body) {
    }
}
