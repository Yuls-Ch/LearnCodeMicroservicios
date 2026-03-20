package com.learncode_backend.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.learncode_backend.dto.ClientCourseDTO;
import com.learncode_backend.model.Course;

public interface CourseService extends ICRUD<Course, UUID> {
	
	// ADMIN
	/*
	Course createCourse(Course course);
	List<Course> findAll();
	List<Course> findAll(String title);
	Course findById(UUID id)
	void deleteCourse(UUID id);
	*/
	
	
	Course updateCourse(UUID id, Course course) throws Exception;
	List<Course> findAll(String title) throws Exception;
	Page<Course> findPaged(Pageable pageable, String title, Boolean published) throws Exception;
	List<Course> findPublished(String title) throws Exception;
	
	//CLIENT
	List<ClientCourseDTO> listPublished() throws Exception;
	ClientCourseDTO getById(UUID id) throws Exception;
	
}
