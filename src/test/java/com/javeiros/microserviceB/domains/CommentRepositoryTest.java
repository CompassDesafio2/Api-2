package com.javeiros.microserviceB.domains;

import com.javeiros.microserviceB.entities.Comment;
import com.javeiros.microserviceB.repository.CommentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static com.javeiros.microserviceB.common.CommentsConstants.*;
import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("it")
@DataMongoTest
@Testcontainers
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setup() {
        mongoTemplate.dropCollection(Comment.class);
    }

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
    public void createComment_With_ValidData_ShouldCreateComment() {
        assertThat(mongoDBContainer.isRunning()).isTrue();

        Comment comment = commentRepository.save(COMMENT);

        System.out.println(comment);

        Comment createdComment = mongoTemplate.findById(comment.getId(), Comment.class);

        assertThat(createdComment).isNotNull();
        assertThat(createdComment.getName()).isEqualTo(comment.getName());
        assertThat(createdComment.getPostId()).isEqualTo(comment.getPostId());
        assertThat(createdComment.getName()).isEqualTo(comment.getName());
        assertThat(createdComment.getBody()).isEqualTo(comment.getBody());
    }


//    @Test
//    public void createComment_With_InvalidData_ShouldThrowException() {
//        assertThat(mongoDBContainer.isRunning()).isTrue();
//
//        assertThatThrownBy(() -> commentRepository.save(INVALID_COMMENT)).isInstanceOf(IllegalArgumentException.class);
//        assertThatThrownBy(() -> commentRepository.save(EMPTY_COMMENTS)).isInstanceOf(IllegalArgumentException.class);
//

//    }

    @Test
    public void findAllComments_With_Comments_ShouldReturnAllComments() {
        assertThat(mongoDBContainer.isRunning()).isTrue();

        for (Comment comment : COMMENT_LIST) {
            mongoTemplate.save(comment);
        }

        List<Comment> comments = commentRepository.findAll();

        assertThat(comments).isNotEmpty();
        assertThat(comments.size()).isEqualTo(COMMENT_LIST.size());
        assertThat(comments.get(0).getName()).isEqualTo(COMMENT_LIST.get(0).getName());
        assertThat(comments.get(0).getPostId()).isEqualTo(COMMENT_LIST.get(0).getPostId());
        assertThat(comments.get(0).getEmail()).isEqualTo(COMMENT_LIST.get(0).getEmail());
        assertThat(comments.get(0).getName()).isEqualTo(COMMENT_LIST.get(0).getName());
        assertThat(comments.get(0).getBody()).isEqualTo(COMMENT_LIST.get(0).getBody());
    }

    @Test
    public void findAllComments_With_EmptyList_ShouldReturnEmptyList() {
        assertThat(mongoDBContainer.isRunning()).isTrue();

        List<Comment> comments = commentRepository.findAll();

        assertThat(comments).isEmpty();

    }



    @Test
    public void findyById_With_ValidData_ShouldFindComment() {
        assertThat(mongoDBContainer.isRunning()).isTrue();

        mongoTemplate.save(COMMENT);

        Optional<Comment> comment = commentRepository.findById(COMMENT.getId());

        assertThat(comment.isPresent()).isTrue();
        assertThat(comment.get().getId()).isEqualTo(COMMENT.getId());
        assertThat(comment.get().getPostId()).isEqualTo(COMMENT.getPostId());
        assertThat(comment.get().getName()).isEqualTo(COMMENT.getName());
        assertThat(comment.get().getEmail()).isEqualTo(COMMENT.getEmail());
        assertThat(comment.get().getBody()).isEqualTo(COMMENT.getBody());

    }








}
