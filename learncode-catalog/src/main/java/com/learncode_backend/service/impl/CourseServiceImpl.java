package com.learncode_backend.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.learncode_backend.dto.ClientCourseDTO;
import com.learncode_backend.model.Course;
import com.learncode_backend.repository.CourseRepository;
import com.learncode_backend.service.CourseService;

@Service
public class CourseServiceImpl 
        extends ICRUDImpl<Course, UUID> 
        implements CourseService {

    private final CourseRepository courseRepo;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepo = courseRepository;
    }

    @Override
    public JpaRepository<Course, UUID> getRepository() {
        return courseRepo;
    }

    @Override
    public List<Course> findAll(String title) throws Exception {
        if (title == null || title.isBlank()) {
            return super.findAll();
        }
        return courseRepo.findByTitle(title);
    }

    @Override
    public Course updateCourse(UUID id, Course course) throws Exception {
        Course existing = findById(id);

        existing.setTitle(course.getTitle());
        existing.setSubtitle(course.getSubtitle());
        existing.setDescription(course.getDescription());
        existing.setIconUrl(course.getIconUrl());
        existing.setCoverUrl(course.getCoverUrl());
        existing.setFree(course.getFree());
        existing.setRequiredPlanCode(course.getRequiredPlanCode());
        existing.setPublished(course.isPublished());

        System.out.println(course);
        
        return courseRepo.save(existing);
    }

    @Override
    public List<Course> findPublished(String title) throws Exception {
        if (title == null || title.isBlank()) {
            return courseRepo.findByPublishedTrue();
        }
        return courseRepo.findByPublishedTrueAndTitle(title);
    }

    @Override
    public Page<Course> findPaged(Pageable pageable, String title, Boolean published) throws Exception{
        return courseRepo.findWithFilters(title, published, pageable);
    }

    @Override
    public List<ClientCourseDTO> listPublished() throws Exception {
        return courseRepo.findAllPublishedWithCounts();
    }

    @Override
    public ClientCourseDTO getById(UUID id) throws Exception {
        ClientCourseDTO dto = courseRepo.findPublishedByIdWithCounts(id);

        if (dto == null) {
            throw new RuntimeException("Curso no encontrado");
        }

        return dto;
    }
}
