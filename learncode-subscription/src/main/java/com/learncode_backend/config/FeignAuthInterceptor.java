package com.learncode_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import feign.RequestInterceptor;

@Configuration
public class FeignAuthInterceptor {

    @Bean
    public RequestInterceptor interceptor() {

        return template -> {

            var auth =
                SecurityContextHolder
                    .getContext()
                    .getAuthentication();

            if (auth instanceof JwtAuthenticationToken jwt) {

                template.header(
                    "Authorization",
                    "Bearer " + jwt.getToken().getTokenValue()
                );
            }
        };
    }
}