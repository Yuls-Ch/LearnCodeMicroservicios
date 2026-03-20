package com.learncode_backend.repository;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learncode_backend.dto.PaymentDTO;
import com.learncode_backend.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
	@Query(value = """
	    SELECT COALESCE(SUM(amount_cents)/100.0, 0) AS total
	    FROM subscription_schema.payments
	    WHERE paid_at >= CURRENT_DATE::timestamp
	      AND paid_at < (CURRENT_DATE + 1)::timestamp
	    """, nativeQuery = true)
	BigDecimal dailyIncome();
	
	boolean existsByStripePaymentIntentId(String sessionId);
	
}
