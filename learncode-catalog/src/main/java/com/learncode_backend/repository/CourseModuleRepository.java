package com.learncode_backend.repository;

import com.learncode_backend.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CourseModuleRepository extends JpaRepository<CourseModule, UUID> {
    List<CourseModule> findByCourseIdOrderByModuleOrderAsc(UUID courseId);
}