package com.javeiros.microserviceB.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO implements Serializable {

    String id;
    String title;
    String body;
}
