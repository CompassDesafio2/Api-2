package com.javeiros.microserviceB.services;

import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.entities.dto.PostDTO;
import com.javeiros.microserviceB.exception.DataBaseException;
import com.javeiros.microserviceB.exception.EntityNotFoundException;
import com.javeiros.microserviceB.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServices {

    @Autowired
    private PostRepository repository;

    public List<Post> findAllPost(){
        return repository.findAll();
    }


    public Post findById(String id) {

        Optional<Post> post = repository.findById(id);
        return post.orElseThrow(() -> new EntityNotFoundException("Post not found"));

    }

    public List<Post> findAll() {
        try {

        return repository.findAll();
        } catch (DataAccessException e) {
            throw new DataBaseException("Database error");
        }
    }

    public Post save(Post post) {
        try {
            return repository.save(post);
        } catch (DataBaseException e) {
            throw new DataBaseException("Database error");
        }
    }

    public Post update(Post obj) {
        try {
            Post newPost = findById(obj.getId());
            updateData(newPost, obj);
            return repository.save(newPost);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Post not found");
        } catch (DataAccessException e) {
            throw new DataBaseException("Database error");
        }
    }

    private void updateData(Post newPost, Post obj) {
        newPost.setTitle(obj.getTitle());
        newPost.setBody(obj.getBody());
    }

    public void delete(String id) {
        try {

            repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
            repository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataBaseException("Database error");
        }

    }

    public Post fromDTO(PostDTO objDTO) {
        try {

        return new Post(objDTO.getId(), objDTO.getUserId(), objDTO.getTitle(), objDTO.getBody());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Post not found");
        } catch (DataAccessException e) {
            throw new DataBaseException("Database error");
        }

    }

}
