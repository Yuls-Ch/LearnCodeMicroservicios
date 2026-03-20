package com.learncode_backend.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.learncode_backend.model.Subscription;

public interface GestionSuscripcionRepository extends JpaRepository<Subscription, UUID> {
	
	@Query("""
		    SELECT s
		    FROM Subscription s
		    WHERE
		        (:plan = 'TODO' OR UPPER(s.planCode) = UPPER(:plan))
		    AND
		        (:status = 'TODO' OR UPPER(s.status) = UPPER(:status))
		    ORDER BY s.startAt ASC
		""")
		Page<Subscription> listarSuscripciones(
		    @Param("plan") String plan,
		    @Param("status") String status,
		    Pageable pageable
		);

    @Modifying
    @Query("""
        UPDATE Subscription s
        SET s.status = 'EXPIRED'
        WHERE s.endAt < CURRENT_TIMESTAMP
        AND s.status = 'ACTIVE'
    """)
    void marcarSuscripcionesExpiradas();
    
    @Modifying
    @Query("""
        UPDATE Subscription s
        SET s.planCode = :plan,
            s.status = 'ACTIVE',
            s.endAt = :endAt,
            s.updatedAt = CURRENT_TIMESTAMP
        WHERE s.id = :id
    """)
    void activarSuscripcion(
        @Param("id") UUID id,
        @Param("plan") String plan,
        @Param("endAt") LocalDateTime endAt
    );

    @Modifying
    @Query("""
        UPDATE Subscription s
        SET s.planCode = :plan,
            s.status = 'CANCELED',
            s.endAt = CURRENT_TIMESTAMP,
            s.updatedAt = CURRENT_TIMESTAMP
        WHERE s.id = :id
    """)
    void cancelarSuscripcion(
        @Param("id") UUID id,
        @Param("plan") String plan
    );
    
  
	Optional<Subscription> findById(UUID id);
    
    @Modifying
    @Query("""
        UPDATE Subscription s
        SET s.planCode = :plan,
            s.status = :status,
            s.updatedAt = CURRENT_TIMESTAMP
        WHERE s.id = :id
    """)
    void actualizarSuscripcion(
        @Param("id") UUID id,
        @Param("plan") String plan,
        @Param("status") String status
    );
}
