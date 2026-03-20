package com.learncode_backend.controller;

import com.learncode_backend.dto.ModuleDTO;
import com.learncode_backend.service.ClientContentService;
import com.learncode_backend.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/client/content")
public class ClientContentController {

    private final ClientContentService clientService;

    public ClientContentController(ClientContentService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/module/{moduleId}/complete")
    public ResponseEntity<ApiResponse<String>> completeModule(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID moduleId) {
        String email = jwt.getClaimAsString("email");
        clientService.markModuleAsCompleted(email, moduleId);
        return new ResponseEntity<>(new ApiResponse<>(true, "Módulo completado", null), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<ModuleDTO>>> getContent(@PathVariable UUID courseId) {
        List<ModuleDTO> data = clientService.getModulesForStudent(courseId);
        return new ResponseEntity<>(new ApiResponse<>(true, "Contenido obtenido", data), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}/progress")
    public ResponseEntity<ApiResponse<List<UUID>>> getProgress(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID courseId) {
        String email = jwt.getClaimAsString("email");
        List<UUID> data = clientService.getCompletedModuleIds(email, courseId);
        return new ResponseEntity<>(new ApiResponse<>(true, "Progreso obtenido", data), HttpStatus.OK);
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<ApiResponse<Map<String, String>>> getFile(@PathVariable UUID fileId) {
        Map<String, String> fileData = clientService.getFileContent(fileId);
        return new ResponseEntity<>(new ApiResponse<>(true, "Archivo obtenido", fileData), HttpStatus.OK);
    }
}