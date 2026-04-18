package com.learncode_backend.repository;

import com.learncode_backend.model.CourseModule;

import feign.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CourseModuleRepository extends JpaRepository<CourseModule, UUID> {
    List<CourseModule> findByCourseIdOrderByModuleOrderAsc(UUID courseId);
    
    @Query("SELECT COUNT(m) FROM CourseModule m WHERE m.courseId = :courseId")
    long countByCourseId(@Param("courseId") UUID courseId);
}