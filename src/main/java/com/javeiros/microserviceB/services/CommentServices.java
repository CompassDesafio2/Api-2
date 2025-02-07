package com.javeiros.microserviceB.services;


import com.javeiros.microserviceB.entities.Comment;
import com.javeiros.microserviceB.entities.dto.CommentDTO;
import com.javeiros.microserviceB.exception.DataBaseException;
import com.javeiros.microserviceB.exception.EntityNotFoundException;
import com.javeiros.microserviceB.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServices {

    @Autowired
    private CommentRepository repository;


    public List<Comment> findAll() {
        try {

            return repository.findAll();
        } catch (DataAccessException e) {
            throw new DataBaseException("Error getting all comments");
        }
    }

    public List<Comment> getCommentsByPost(String postId) {
        try {
            return repository.findByPostId(postId);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("Comment not found");
        }
    }

    public Comment findById(String id) {
        Optional<Comment> comment = repository.findById(id);
        return comment.orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }

    public Comment addComment(String postId, Comment comment) {
        try {

            comment.setPostId(postId);
            return repository.save(comment);
        } catch (DataBaseException e) {
            throw new DataBaseException("Database error");
        }
    }

    public Comment updateComment(String id, Comment obj) {
        try {
            Comment newComment = findById(obj.getId());
            updateData(newComment, obj);
            return repository.save(newComment);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Comment not found");
        } catch (DataAccessException e) {
            throw new DataBaseException("Database error");
        }

    }

    public void delete(String id) {
        try {

            repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
            repository.deleteById(id);
        } catch (DataBaseException e) {
            throw new DataBaseException("Database error");
        }
    }


    public void updateData(Comment newObj, Comment obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
        newObj.setBody(obj.getBody());

    }

    public Comment fromDTO(CommentDTO obj) {
        try {

            return new Comment(obj.getId(), obj.getPostId(), obj.getName(), obj.getEmail(), obj.getBody());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Comment not found");
        } catch (DataBaseException e) {
            throw new DataBaseException("Database error");
        }

    }

}
