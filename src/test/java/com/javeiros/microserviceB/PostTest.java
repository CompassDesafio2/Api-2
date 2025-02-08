package com.javeiros.microserviceB;

import com.javeiros.microserviceB.entities.Post;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    @Test
    public void testPostConstructorAndGetters() {
        Post post = new Post("1", "100", "Test Title", "Test Body");

        assertEquals("1", post.getId());
        assertEquals("100", post.getUserId());
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Body", post.getBody());
    }

    @Test
    public void testPostSetters() {
        Post post = new Post();
        post.setId("2");
        post.setUserId("200");
        post.setTitle("Another Title");
        post.setBody("Another Body");

        assertEquals("2", post.getId());
        assertEquals("200", post.getUserId());
        assertEquals("Another Title", post.getTitle());
        assertEquals("Another Body", post.getBody());
    }

    @Test
    public void testToString() {
        Post post = new Post("3", "300", "Sample Title", "Sample Body");
        String expectedString = "Post(id=3, userId=300, title=Sample Title, body=Sample Body)";
        assertEquals(expectedString, post.toString());
    }
}
