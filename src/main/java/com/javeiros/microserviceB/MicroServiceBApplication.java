package com.javeiros.microserviceB;


import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.entities.dto.PostDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SpringBootApplication
@EnableFeignClients

public class MicroServiceBApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceBApplication.class, args);
    }

    @Bean
    public CommandLineRunner helloWorldRunner() {
        return args -> System.out.println("Hello, World!");
    }

}

