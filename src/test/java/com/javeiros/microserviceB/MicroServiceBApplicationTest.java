package com.javeiros.microserviceB;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MicroServiceBApplicationTest {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> MicroServiceBApplication.main(new String[]{}));
    }
}

