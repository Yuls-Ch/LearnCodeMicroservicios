package com.learncode_backend.repository;

import com.learncode_backend.model.StudentProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StudentProgressRepository extends JpaRepository<StudentProgress, UUID> {

	@Query("""
			    SELECT DISTINCT sp.courseId
			    FROM StudentProgress sp
			    WHERE sp.userId = :userId
			""")
	List<UUID> findDistinctCourseIdsByUserId(UUID userId);

	List<StudentProgress> findByUserId(UUID userId);

	List<StudentProgress> findByUserIdAndCourseId(UUID userId, UUID courseId);

	boolean existsByUserIdAndModuleId(UUID userId, UUID moduleId);

	long countByCourseIdAndUserId(UUID courseId, UUID userId);
}