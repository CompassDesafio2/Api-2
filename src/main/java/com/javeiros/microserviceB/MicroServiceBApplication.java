package com.javeiros.microserviceB;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroServiceBApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceBApplication.class, args);
    }

    @Bean
    public CommandLineRunner helloWorldRunner() {
        return args -> System.out.println("Hello, World!");
    }
}
