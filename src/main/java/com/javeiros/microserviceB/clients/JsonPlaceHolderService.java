package com.javeiros.microserviceB.clients;

import com.javeiros.microserviceB.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsonPlaceHolderService {

    private final JsonPlaceHolderClient client;

    @Autowired
    public JsonPlaceHolderService(JsonPlaceHolderClient client) {
        this.client = client;
    }

    public Post fetchPost(Long id) {
        return client.getPostById(id);
    }
}
