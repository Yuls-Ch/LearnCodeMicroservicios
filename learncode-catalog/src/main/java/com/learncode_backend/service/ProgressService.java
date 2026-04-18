package com.learncode_backend.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.learncode_backend.dto.CourseProgressDTO;
import com.learncode_backend.model.Course;
import com.learncode_backend.model.StudentProgress;
import com.learncode_backend.repository.CourseModuleRepository;
import com.learncode_backend.repository.CourseRepository;
import com.learncode_backend.repository.StudentProgressRepository;
import com.learncode_backend.utils.ModeloNotFoundException;

@Service
public class ProgressService {

	private final StudentProgressRepository progressRepo;
	private final CourseModuleRepository moduleRepo;
	private final CourseRepository courseRepo;

	public ProgressService(	StudentProgressRepository progressRepo, 
						 	CourseModuleRepository moduleRepo,
							CourseRepository courseRepo) 
	{
		this.progressRepo = progressRepo;
		this.moduleRepo = moduleRepo;
		this.courseRepo = courseRepo;
		
	}

	public List<CourseProgressDTO> getCourseProgressForUser(UUID userId) {
		
		List<UUID> courseIds = progressRepo.findDistinctCourseIdsByUserId(userId);
		
		if (courseIds.isEmpty()) return List.of();
	    List<Course> courses = courseRepo.findAllById(courseIds);
		
	    return courses.stream().map(course -> {

	        UUID courseId = course.getId();

	        long total = moduleRepo.countByCourseId(courseId);
	        long completed = progressRepo.countByCourseIdAndUserId(courseId, userId);

	        int pct = total == 0 ? 0 : (int) Math.round((completed * 100.0) / total);

	        return new CourseProgressDTO(
	                courseId,
	                course.getTitle(),
	                course.getIconUrl(),
	                (int) total,
	                (int) completed,
	                pct
	        );

	    }).toList();
	}
}