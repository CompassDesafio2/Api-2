package com.javeiros.microserviceB.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class SpringSecurityConfig {

    public static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html",
            "/api/v1/**",
            "/api/v1/docs/**",
            "/api/v1/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
            "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(crsf -> crsf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                        .anyRequest().permitAll()

                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults())
                .build();
    }


}
