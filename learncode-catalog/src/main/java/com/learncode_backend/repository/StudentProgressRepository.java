package com.learncode_backend.repository;

import com.learncode_backend.model.StudentProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface StudentProgressRepository extends JpaRepository<StudentProgress, UUID> {
    List<StudentProgress> findByUserIdAndCourseId(UUID userId, UUID courseId);
    boolean existsByUserIdAndModuleId(UUID userId, UUID moduleId);
}