package com.learncode_backend.controller;

import com.learncode_backend.dto.CreateFileDTO;
import com.learncode_backend.dto.CreateModuleDTO;
import com.learncode_backend.dto.ModuleDTO;
import com.learncode_backend.model.CourseModule;
import com.learncode_backend.service.ContentService;
import com.learncode_backend.utils.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/content")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<List<ModuleDTO>>> getByCourse(@PathVariable UUID courseId) {
        List<ModuleDTO> data = contentService.getModulesByCourse(courseId);
        return new ResponseEntity<>(new ApiResponse<>(true, "Módulos cargados", data), HttpStatus.OK);
    }

    @PostMapping("/module")
    public ResponseEntity<ApiResponse<CourseModule>> createModule(@Valid @RequestBody CreateModuleDTO dto) {
        CourseModule created = contentService.createModule(dto.courseId(), dto.title(), dto.order());
        return new ResponseEntity<>(new ApiResponse<>(true, "Módulo creado", created), HttpStatus.CREATED);
    }

    @DeleteMapping("/module/{id}")
    public ResponseEntity<ApiResponse<String>> deleteModule(@PathVariable UUID id) {
        contentService.deleteModule(id);
        return new ResponseEntity<>(new ApiResponse<>(true, "Módulo eliminado", null), HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestBody CreateFileDTO dto) {
        contentService.uploadFile(dto);
        return new ResponseEntity<>(new ApiResponse<>(true, "Archivo subido", null), HttpStatus.OK);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<ApiResponse<Map<String, String>>> getFile(@PathVariable UUID id) {
        Map<String, String> fileData = contentService.getFileContent(id);
        return new ResponseEntity<>(new ApiResponse<>(true, "Archivo recuperado", fileData), HttpStatus.OK);
    }

    @PutMapping("/module/{id}")
    public ResponseEntity<ApiResponse<CourseModule>> updateModule(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        String newTitle = body.get("title");
        CourseModule updated = contentService.updateModule(id, newTitle);
        return new ResponseEntity<>(new ApiResponse<>(true, "Módulo actualizado", updated), HttpStatus.OK);
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity<ApiResponse<String>> deleteFile(@PathVariable UUID id) {
        contentService.deleteFile(id);
        return new ResponseEntity<>(new ApiResponse<>(true, "Archivo eliminado", null), HttpStatus.OK);
    }
}