package com.learncode_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/api/admin/**").authenticated() 
            	    .requestMatchers("/api/client/**").authenticated() 
            	    .requestMatchers("/api/courses/**").permitAll()   
            	    .anyRequest().permitAll()
            	)

            .oauth2ResourceServer(oauth -> oauth
                .jwt(jwt -> jwt
                    .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                )
            );
        return http.build();
    }
}
