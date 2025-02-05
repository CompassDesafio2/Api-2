package com.javeiros.microserviceB.services;

import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.entities.dto.PostDTO;
import com.javeiros.microserviceB.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return post.orElseThrow(() -> new RuntimeException("Post Not Find"));
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public Post update(Post obj) {
        Post newPost = findById(obj.getId());
        updateData(newPost, obj);
        return repository.save(newPost);
    }

    private void updateData(Post newPost, Post obj) {
        newPost.setTitle(obj.getTitle());
        newPost.setBody(obj.getBody());
    }

    public void delete(String id) {
        repository.findById(id).orElseThrow(() -> new RuntimeException("Post Not Find"));
        repository.deleteById(id);

    }

    public Post fromDTO(PostDTO objDTO) {
        return new Post(objDTO.getId(), objDTO.getUserId(), objDTO.getTitle(), objDTO.getBody());

    }

}
