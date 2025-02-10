package com.javeiros.microserviceB.config;


import com.javeiros.microserviceB.entities.Comment;
import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.repository.CommentRepository;
import com.javeiros.microserviceB.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class Instatiantion implements CommandLineRunner {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void run(String... args) throws Exception {

        Post p1 = new Post("1", "1", "Teste de Praia", " Teste 1");
        Post p2 = new Post("2", "2", "Teste de Praia", " Teste 2");
        Post p3 = new Post("3", "3", "Teste de Praia", " Teste 3");
        Post p4 = new Post("4", "4", "Teste de Praia", " Teste 4");
        postRepository.saveAll(Arrays.asList(p1, p2, p3, p4));


        Comment c1 = new Comment("1", "1", "Alex",  "email@email.com", "Teste 1");
        Comment c2 = new Comment("2", "2", "Bruno",  "email@email.com", "Teste 2");
        Comment c3 = new Comment("3", "1", "Cesar",  "email@email.com", "Teste 3");
        Comment c4 = new Comment("4", "2", "Daniel",  "email@email.com", "Teste 4");

        commentRepository.saveAll(Arrays.asList(c1, c2, c3, c4));

        System.out.println("Documento inserido com sucesso!");



    }
}