package com.learncode_backend.controller;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.dto.ClientCourseDTO;
import com.learncode_backend.service.CourseService;
import com.learncode_backend.utils.ApiResponse;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	private final CourseService courseService;
	
	@Autowired
	private ModelMapper mapper;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<ClientCourseDTO>>> listPublished() throws Exception {
		List<ClientCourseDTO> result = courseService.listPublished();
		ApiResponse<List<ClientCourseDTO>> response = new ApiResponse<>(true, "Listado de cursos publicados", result);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ClientCourseDTO>> getById(@PathVariable UUID id) throws Exception {
		ClientCourseDTO dto = courseService.getById(id);
		ApiResponse<ClientCourseDTO> response = new ApiResponse<>(true, "Curso encontrado", dto);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
