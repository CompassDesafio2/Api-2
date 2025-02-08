package com.javeiros.microserviceB;

import com.javeiros.microserviceB.clients.JsonPlaceHolderService;
import com.javeiros.microserviceB.controller.PostController;
import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.entities.dto.PostDTO;
import com.javeiros.microserviceB.services.PostServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostServices postServices;

    @Mock
    private JsonPlaceHolderService jsonApiServices;

    private Post post;
    private PostDTO postDTO;

    @BeforeEach
    void setUp() {
        post = new Post("1", "Title", "Body");
        postDTO = new PostDTO(post);
    }

    @Test
    public void fetchData() {
        ResponseEntity<Void> response = postController.fetchData();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getAllPost() {
        when(postServices.findAll()).thenReturn(Arrays.asList(post));
        ResponseEntity<List<PostDTO>> response = postController.getAllPost();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void getPost() {
        when(postServices.findById("1")).thenReturn(post);
        ResponseEntity<Post> response = postController.getPost("1");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(post, response.getBody());
    }

    @Test
    public void insertPost() {
        when(postServices.fromDTO(any(PostDTO.class))).thenReturn(post);
        when(postServices.save(any(Post.class))).thenReturn(post);
        ResponseEntity<Post> response = postController.insertPost(postDTO);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void updatePost() {
        when(postServices.fromDTO(any(PostDTO.class))).thenReturn(post);
        when(postServices.update(any(Post.class))).thenReturn(post);
        ResponseEntity<Post> response = postController.updatePost("1", postDTO);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void deletePost() {
        ResponseEntity<Void> response = postController.deletePost("1");
        assertEquals(204, response.getStatusCodeValue());
    }
}