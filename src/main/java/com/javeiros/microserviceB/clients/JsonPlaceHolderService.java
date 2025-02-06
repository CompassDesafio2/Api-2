package com.javeiros.microserviceB.clients;

import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.repository.PostRepository;
import com.javeiros.microserviceB.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonPlaceHolderService {

    @Autowired
    private PostServices postServices;

    private final JsonPlaceHolderClient client;

    @Autowired
    public JsonPlaceHolderService(JsonPlaceHolderClient client) {
        this.client = client;
    }

    public Post fetchPost(Long id) {
        return client.getPostById(id);
    }

    public void fetchData() {
        List<Post> posts = client.getPosts();

        for (Post post : posts) {
            postServices.save(post);

        }

    }
}
