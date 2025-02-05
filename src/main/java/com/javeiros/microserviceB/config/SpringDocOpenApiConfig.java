package com.javeiros.microserviceB.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("MicroserviceB API")
                                .description("Endpoints da API B para Consumo pela Api A, " +
                                        "Com objetivo de realizar um CRUD da criação de POSTS e " +
                                        "consumir a API JSONPLACEHOLDER")
                                .version("1.0")
                );

    }

}
