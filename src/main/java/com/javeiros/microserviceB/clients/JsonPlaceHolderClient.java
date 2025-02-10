package com.javeiros.microserviceB.clients;

import com.javeiros.microserviceB.entities.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient(name = "jsonPlaceholderClient", url = "https://jsonplaceholder.typicode.com")
public interface JsonPlaceHolderClient {


    @GetMapping("/posts")
    List<Post> getPosts();

    @GetMapping("/posts/{id}")
    Post getPostById(@PathVariable Long id);

    @PostMapping("/posts")
    Post createPost(@RequestBody Post post);
}