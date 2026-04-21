package com.learncode_backend.repository;

import com.learncode_backend.dto.StudentPlanDTO;
import com.learncode_backend.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

	Optional<Subscription> findByUserId(UUID userId);

// Graficos
	@Query("""
			    SELECT new com.learncode_backend.dto.StudentPlanDTO(
			        s.planCode,
			        COUNT(s.userId)
			    )
			    FROM Subscription s
			    WHERE s.status = 'ACTIVE'
			    GROUP BY s.planCode
			    ORDER BY COUNT(s.userId) DESC
			""")
	List<StudentPlanDTO> studentsByPlan();

	
	
	
}