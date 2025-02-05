package com.javeiros.microserviceB.config;


import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class Instatiantion implements CommandLineRunner {


    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        Post p1 = new Post("1", "Teste de Praia", " Teste 1");
        Post p2 = new Post("2", "Teste de Praia", " Teste 2");
        Post p3 = new Post("3", "Teste de Praia", " Teste 3");
        Post p4 = new Post("4", "Teste de Praia", " Teste 4");
        postRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

        System.out.println("Documento inserido com sucesso!");



    }
}