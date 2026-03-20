package com.learncode_backend.service;

import java.util.List;
import java.util.UUID;
import com.learncode_backend.dto.PaymentDTO;

public interface PaymentService {

	void registerPayment(
		    String userId,
		    String planCode,
		    Integer amount,
		    String currency,
		    String paymentIntentId,
		    String sessionId,
		    Long paidAt
		);

    List<PaymentDTO> getAllPayments();
}
