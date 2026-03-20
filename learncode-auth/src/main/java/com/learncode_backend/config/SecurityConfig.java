package com.learncode_backend.config;

import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import com.learncode_backend.model.User;
import com.learncode_backend.repository.UserRepository;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())

            .authorizeHttpRequests(auth -> auth
            		
             
            	    // Stripe
            	    .requestMatchers("/api/stripe/webhook").permitAll()  
            	    .requestMatchers("/api/stripe/**").authenticated()
            	    
            	    // Auth
            	    .requestMatchers("/api/auth/internal/**").permitAll()
            	    .requestMatchers("/api/auth/**").authenticated()
            	    
            	    // Public
            	    .requestMatchers("/api/courses/**").authenticated()
            	    .requestMatchers("/api/plans").permitAll()
            	    .requestMatchers("/api/plans/**").permitAll()
            	    .requestMatchers("/api/payments/**").permitAll()

            	    //  Pagos SOLO ADMIN
            	    .requestMatchers("/api/payments/**").hasAuthority("ADMIN")

            	    // Admin 
            	    .requestMatchers("/api/admin/**").authenticated()
            	    
            	    // Client & Others 
            	    .anyRequest().authenticated()
            	)

            .oauth2ResourceServer(oauth -> oauth
                .jwt(jwt -> jwt
                    .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );

        return http.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String email = jwt.getClaim("email");
            String name  = jwt.getClaim("name");
            String sub   = jwt.getSubject();
            String photo = jwt.getClaim("picture");

            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setId(UUID.randomUUID());
                newUser.setGoogleSub(sub);
                newUser.setEmail(email);
                newUser.setFullName(name);
                newUser.setPhoto(photo);
                newUser.setRole("USER");
                newUser.setStatus("ACTIVE");
                return userRepository.save(newUser);
            });

            return List.of(new SimpleGrantedAuthority(user.getRole()));
        });
        return converter;
    }
}