package com.learncode_backend.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.learncode_backend.config.FeignAuthInterceptor;
import com.learncode_backend.dto.UserDTO;

@FeignClient(
	    name = "AUTH-SERVICE",
	    configuration = FeignAuthInterceptor.class
)public interface AuthClient {

    @GetMapping("/api/auth/internal/user/{id}")
    UserDTO getUser(@PathVariable("id") UUID id);
    
    @GetMapping("/api/auth/me")
    UserDTO getMe();
}
