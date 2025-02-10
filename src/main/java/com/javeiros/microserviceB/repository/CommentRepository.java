package com.javeiros.microserviceB.repository;

import com.javeiros.microserviceB.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId);

    Optional<Comment> findById(String id);
}
