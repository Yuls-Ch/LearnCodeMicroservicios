package com.learncode_backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.client.AuthClient;
import com.learncode_backend.dto.CourseProgressDTO;
import com.learncode_backend.service.ProgressService;
import com.learncode_backend.utils.ApiResponse;

@RestController
@RequestMapping("/api/client")
public class StudentProgressController {
	
	private final ProgressService progressS;
	private final AuthClient authClient;
	
	public StudentProgressController(ProgressService progressService, AuthClient authClient) {
        this.progressS = progressService;
        this.authClient = authClient;
    }

    @GetMapping("/progress")
    public ResponseEntity<ApiResponse<List<CourseProgressDTO>>> getProgress(
    		 @AuthenticationPrincipal Jwt jwt) {
    	String email = jwt.getClaimAsString("email");
        UUID userId = authClient.getUserIdByEmail(email).getData();
    	
    	List<CourseProgressDTO> data = progressS.getCourseProgressForUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Progreso cargado", data));
    }
}
 