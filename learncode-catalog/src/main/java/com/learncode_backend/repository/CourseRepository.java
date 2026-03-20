package com.learncode_backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learncode_backend.dto.ClientCourseDTO;
import com.learncode_backend.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

	// Filtros
	List<Course> findByTitle(String title);

	List<Course> findByPublishedTrue();

	List<Course> findByPublishedTrueAndTitle(String title);

	// Conteo
	@Query("SELECT COUNT(c) FROM Course c WHERE c.published = true")
	long countPublished();

	@Query("SELECT c FROM Course c WHERE " + "(:title IS NULL OR CAST(c.title AS string) ILIKE %:title%) AND "
			+ "(:published IS NULL OR c.published = :published)")
	Page<Course> findWithFilters(@Param("title") String title, @Param("published") Boolean published,
			Pageable pageable);

	@Query("""
		    SELECT new com.learncode_backend.dto.ClientCourseDTO(
		        c.id,
		        c.title,
		        c.subtitle,
		        c.iconUrl,
		        c.coverUrl,
		        c.free,
		        c.requiredPlanCode,
		        COUNT(DISTINCT m),
		        COUNT(DISTINCT f)
		    )
		    FROM Course c
		    LEFT JOIN c.modules m
		    LEFT JOIN m.files f
		    WHERE c.published = true
		    GROUP BY c.id, c.title, c.subtitle, c.iconUrl, c.coverUrl, c.free, c.requiredPlanCode
		""")
		List<ClientCourseDTO> findAllPublishedWithCounts();


	@Query("SELECT new com.learncode_backend.dto.ClientCourseDTO("
			+ "c.id, c.title, c.subtitle, c.iconUrl, c.coverUrl, c.free, c.requiredPlanCode, "
			+ "(SELECT COUNT(m) FROM CourseModule m WHERE m.courseId = c.id), "
			+ "(SELECT COUNT(f) FROM ModuleFile f WHERE f.module.courseId = c.id)) "
			+ "FROM Course c WHERE c.id = :id AND c.published = true")
	ClientCourseDTO findPublishedByIdWithCounts(@Param("id") UUID id);

}
