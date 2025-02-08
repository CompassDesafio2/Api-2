package com.javeiros.microserviceB;

import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.entities.dto.PostDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PostDTOTest {

    private Post post;

    @Before
    public void setUp() {
        post = new Post();
        post.setId("1");
        post.setUserId("100");
        post.setTitle("Test Title");
        post.setBody("Test Body");
    }

    @Test
    public void testPostDTOConstructor() {
        PostDTO postDTO = new PostDTO(post);

        Assert.assertEquals("1", postDTO.getId());
        Assert.assertEquals("100", postDTO.getUserId());
        Assert.assertEquals("Test Title", postDTO.getTitle());
        Assert.assertEquals("Test Body", postDTO.getBody());
    }

    @Test
    public void testPostDTOSettersAndGetters() {
        PostDTO postDTO = new PostDTO();
        postDTO.setId("2");
        postDTO.setUserId("200");
        postDTO.setTitle("New Title");
        postDTO.setBody("New Body");

        Assert.assertEquals("2", postDTO.getId());
        Assert.assertEquals("200", postDTO.getUserId());
        Assert.assertEquals("New Title", postDTO.getTitle());
        Assert.assertEquals("New Body", postDTO.getBody());
    }
}