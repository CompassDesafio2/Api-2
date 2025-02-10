package com.javeiros.microserviceB;

import com.javeiros.microserviceB.entities.Comment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static com.javeiros.microserviceB.common.CommentsConstants.COMMENT_DTO;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentsiT {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TestRestTemplate restTemplate;


    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6.0"));

    @BeforeAll
    static void startContainer() {
        // Inicia o container do MongoDB
        mongoDBContainer.start();
        System.setProperty("MONGO_URI", mongoDBContainer.getReplicaSetUrl()); // Define a URL de conexão
    }

    @AfterAll
    static void stopContainer() {
        // Para o container depois dos testes
        mongoDBContainer.stop();
    }


    @Test
    public void contextLoads() {
    }



    @Test
    public void createComment() {
        ResponseEntity<Comment> response = restTemplate.postForEntity("/comments/postId", COMMENT_DTO, Comment.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(COMMENT_DTO.getName());
        assertThat(response.getBody().getPostId()).isEqualTo(COMMENT_DTO.getPostId());
        assertThat(response.getBody().getEmail()).isEqualTo(COMMENT_DTO.getEmail());
        assertThat(response.getBody().getBody()).isEqualTo(COMMENT_DTO.getBody());


    }
}
