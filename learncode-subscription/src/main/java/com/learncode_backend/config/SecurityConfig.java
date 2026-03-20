package com.learncode_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())

            .authorizeHttpRequests(auth -> auth
                // Admin
                .requestMatchers("/api/subscription/**").permitAll()
                .requestMatchers("/api/admin/**").authenticated()
                
                // Públicos
                .requestMatchers("/api/plans/**").authenticated()
                .requestMatchers("/api/courses/**").permitAll()
                .requestMatchers("/api/stripe/webhook").permitAll()
                .requestMatchers("/ws/**").permitAll()


                // Protegidos
                .anyRequest().authenticated()
            )

            // Nueva forma (no deprecated)
            .oauth2ResourceServer(oauth ->
                oauth.jwt(Customizer.withDefaults())
            );

        return http.build();
    }
    
}
