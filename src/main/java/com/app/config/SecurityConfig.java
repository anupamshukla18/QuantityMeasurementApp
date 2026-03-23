package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF for REST API
            .csrf(csrf->csrf.disable())

            // Allow all requests for development
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/v1/quantities/**",  // REST API endpoints
                    "/h2-console/**",          // H2 Console
                    "/swagger-ui/**",          // Swagger UI
                    "/swagger-ui.html",        // Swagger UI HTML
                    "/api-docs/**",            // OpenAPI docs
                    "/actuator/**"             // Actuator endpoints
                ).permitAll()
                .anyRequest().permitAll()
            )

            // Allow H2 console frames
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable())
            );

        return http.build();
    }

}
