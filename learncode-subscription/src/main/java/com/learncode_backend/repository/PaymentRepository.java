package com.learncode_backend.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learncode_backend.dto.IncomePlanDTO;
import com.learncode_backend.dto.MonthlyIncomeDTO;
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

	// Graficas
	@Query("""
			    SELECT new com.learncode_backend.dto.IncomePlanDTO(
			        p.planCode,
			        SUM(p.amountCents) / 100.0
			    )
			    FROM Payment p
			    WHERE p.status = 'PAID'
			    GROUP BY p.planCode
			    ORDER BY SUM(p.amountCents) DESC
			""")
	List<IncomePlanDTO> incomeByPlan();

	@Query("""
		    SELECT new com.learncode_backend.dto.MonthlyIncomeDTO(
		        EXTRACT(MONTH FROM p.paidAt),
		        CAST(SUM(p.amountCents) AS double) / 100
		    )
		    FROM Payment p
		    WHERE p.status = 'PAID'
		      AND p.paidAt IS NOT NULL
		    GROUP BY EXTRACT(MONTH FROM p.paidAt)
		    ORDER BY EXTRACT(MONTH FROM p.paidAt)
		""")
		List<MonthlyIncomeDTO> incomeByMonth();
}
