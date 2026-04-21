package com.learncode_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learncode_backend.dto.CourseViewsDTO;
import com.learncode_backend.repository.CourseRepository;

@Service
public class StatisticsService {

	private final CourseRepository courseRepository;
	
	public StatisticsService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	// Cursos más vistos
	public List<CourseViewsDTO> getCoursesMostViewed(){
		return courseRepository.findCoursesMostViewed();
	}
	
}
