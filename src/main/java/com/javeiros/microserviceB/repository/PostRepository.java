package com.javeiros.microserviceB.repository;

import com.javeiros.microserviceB.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository  extends MongoRepository<Post, String> {
}
