package com.javeiros.microserviceB;

import com.javeiros.microserviceB.entities.Comment;
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

import static com.javeiros.microserviceB.common.CommentsConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class CommentsIT {


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
        mongoTemplate.save(COMMENT);
        for (Comment comment : COMMENT_LIST) {
            mongoTemplate.save(comment);
        }
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection(Comment.class);
    }

    @Test
    public void createComment() {
        Comment response = webTestClient.post()
                .uri("/comments/{postId}", COMMENT_DTO.getPostId())
                .bodyValue(COMMENT_DTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Comment.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();  // ID pode ser gerado pelo MongoDB
        assertThat(response.getPostId()).isEqualTo(COMMENT_DTO.getPostId());
        assertThat(response.getName()).isEqualTo(COMMENT_DTO.getName());
        assertThat(response.getEmail()).isEqualTo(COMMENT_DTO.getEmail());
        assertThat(response.getBody()).isEqualTo(COMMENT_DTO.getBody());
    }

    @Test
    public void getCommentById_WithValidId_ReturnComment() {

        Comment comment = webTestClient.get().uri("/comments/{id}", COMMENT_DTO.getId())
                .exchange().expectStatus().isOk()
                .expectBody(Comment.class)
                .returnResult().getResponseBody();

        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(COMMENT.getId());
        assertThat(comment.getPostId()).isEqualTo(COMMENT.getPostId());
        assertThat(comment.getName()).isEqualTo(COMMENT.getName());
        assertThat(comment.getEmail()).isEqualTo(COMMENT.getEmail());
        assertThat(comment.getBody()).isEqualTo(COMMENT.getBody());
    }

    @Test
    public void getCommentsOf_A_POST_WithValidId_ReturnComment() {

        List<Comment> comments = webTestClient.get().uri("/comments/post/{postId}", COMMENT.getPostId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Comment.class)
                .returnResult()
                .getResponseBody();

        assertThat(comments).isNotNull();
        assertThat(comments).isNotEmpty();
    }
}
