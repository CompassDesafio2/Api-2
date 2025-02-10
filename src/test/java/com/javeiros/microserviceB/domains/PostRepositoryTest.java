package com.javeiros.microserviceB.domains;

import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.repository.PostRepository;
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

import static com.javeiros.microserviceB.common.PostConstants.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("it")
@DataMongoTest
@Testcontainers
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @BeforeEach
    void setup() {
        mongoTemplate.dropCollection(Post.class);
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
    public void createPost_With_ValidData_ShouldCreatePost() {
        assertThat(mongoDBContainer.isRunning()).isTrue();

        Post post = postRepository.save(POST);

        System.out.println(post);

        Post createdPost = mongoTemplate.findById(post.getId(), Post.class);

        assertThat(createdPost).isNotNull();
        assertThat(createdPost.getId()).isEqualTo(post.getId());
        assertThat(createdPost.getUserId()).isEqualTo(post.getUserId());
        assertThat(createdPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(createdPost.getBody()).isEqualTo(post.getBody());
    }


//    @Test
//    public void createPost_With_InvalidData_ShouldThrowException() {
//        assertThat(mongoDBContainer.isRunning()).isTrue();
//
//        assertThatThrownBy(() -> postRepository.save(INVALID_POST)).isInstanceOf(IllegalArgumentException.class);
//        assertThatThrownBy(() -> postRepository.save(EMPTY_POSTS)).isInstanceOf(IllegalArgumentException.class);
//

//    }

    @Test
    public void findAllPosts_With_Posts_ShouldReturnAllPosts() {
        assertThat(mongoDBContainer.isRunning()).isTrue();

        for (Post comment : POST_LIST) {
            mongoTemplate.save(comment);
        }

        List<Post> comments = postRepository.findAll();

        assertThat(comments).isNotEmpty();
        assertThat(comments.size()).isEqualTo(POST_LIST.size());
        assertThat(comments.get(0).getId()).isEqualTo(POST_LIST.get(0).getId());
        assertThat(comments.get(0).getUserId()).isEqualTo(POST_LIST.get(0).getUserId());
        assertThat(comments.get(0).getTitle()).isEqualTo(POST_LIST.get(0).getTitle());
        assertThat(comments.get(0).getBody()).isEqualTo(POST_LIST.get(0).getBody());
    }

    @Test
    public void findAllPosts_With_EmptyList_ShouldReturnEmptyList() {
        assertThat(mongoDBContainer.isRunning()).isTrue();

        List<Post> comments = postRepository.findAll();

        assertThat(comments).isEmpty();

    }

    @Test
    public void findyById_With_ValidData_ShouldFindPost() {
        assertThat(mongoDBContainer.isRunning()).isTrue();

        mongoTemplate.save(POST);

        Optional<Post> comment = postRepository.findById(POST.getId());

        assertThat(comment.isPresent()).isTrue();
        assertThat(comment.get().getId()).isEqualTo(POST.getId());
        assertThat(comment.get().getUserId()).isEqualTo(POST.getUserId());
        assertThat(comment.get().getTitle()).isEqualTo(POST.getTitle());
        assertThat(comment.get().getBody()).isEqualTo(POST.getBody());

    }
}