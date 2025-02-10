package com.javeiros.microserviceB;


import com.javeiros.microserviceB.entities.Post;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

import static com.javeiros.microserviceB.common.PostConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class PostsIT {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private WebTestClient webTestClient;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6.0"));

    @BeforeAll
    static void startContainer() {
        mongoDBContainer.start();
        System.setProperty("MONGO_URI", mongoDBContainer.getReplicaSetUrl());
    }

    @AfterAll
    static void stopContainer() {
        mongoDBContainer.stop();
    }

    @BeforeEach
    void setup() {
        mongoTemplate.save(POST);
        for (Post post : POST_LIST) {
            mongoTemplate.save(post);
        }
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Post.class);
    }

    @Test
    public void createPost() {
        Post response = webTestClient.post()
                .uri("/posts")
                .bodyValue(POSTDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Post.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getUserId()).isEqualTo(POSTDTO.getUserId());
        assertThat(response.getTitle()).isEqualTo(POSTDTO.getTitle());
        assertThat(response.getBody()).isEqualTo(POSTDTO.getBody());
    }

    @Test
    public void getPostById_WithValidId_ReturnPost() {

        Post post = webTestClient.get().uri("/posts/{id}", POSTDTO.getId())
                .exchange().expectStatus().isOk()
                .expectBody(Post.class)
                .returnResult().getResponseBody();

        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(POST.getId());
        assertThat(post.getUserId()).isEqualTo(POST.getUserId());
        assertThat(post.getTitle()).isEqualTo(POST.getTitle());
        assertThat(post.getBody()).isEqualTo(POST.getBody());
    }

}