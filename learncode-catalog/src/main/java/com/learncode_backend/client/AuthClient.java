package com.learncode_backend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.learncode_backend.config.FeignConfig;
import com.learncode_backend.utils.ApiResponse;
import java.util.UUID;

@FeignClient(name = "AUTH-SERVICE", configuration = FeignConfig.class) 
public interface AuthClient {
    @GetMapping("/api/auth/users/id/{email}") 
    ApiResponse<UUID> getUserIdByEmail(@PathVariable("email") String email);
}