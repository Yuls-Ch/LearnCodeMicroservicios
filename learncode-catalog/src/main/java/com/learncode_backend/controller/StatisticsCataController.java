package com.learncode_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.dto.CourseViewsDTO;
import com.learncode_backend.service.StatisticsService;
import com.learncode_backend.utils.ApiResponse;

@RestController
@RequestMapping("/api/admin/statisticsCat")
public class StatisticsCataController {

	private final StatisticsService service;
	
	public StatisticsCataController(StatisticsService service) {
		this.service = service;
	}
	
	@GetMapping("/most-viewed-courses")
	public ResponseEntity<ApiResponse<List<CourseViewsDTO>>> mostViewedCourses() throws Exception {
		
		List<CourseViewsDTO> lista = service.getCoursesMostViewed();
		
		ApiResponse<List<CourseViewsDTO>> response = 
				new ApiResponse<>(true, "Cursos más vistos", lista);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
