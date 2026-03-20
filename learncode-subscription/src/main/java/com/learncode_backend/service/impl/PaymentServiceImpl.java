package com.learncode_backend.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.learncode_backend.client.AuthClient;
import com.learncode_backend.dto.PaymentDTO;
import com.learncode_backend.dto.UserDTO;
import com.learncode_backend.model.Payment;
import com.learncode_backend.repository.PaymentRepository;
import com.learncode_backend.service.PaymentService;

@Service	
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository repo;
	private final AuthClient authClient;

    public PaymentServiceImpl(
        PaymentRepository repo,
        AuthClient authClient
    ) {
        this.repo = repo;
        this.authClient = authClient;
    }

    
	@Override
    public void registerPayment(
        String userId,
        String planCode,
        Integer amount,
        String currency,
        String paymentIntentId,
        String sessionId,
        Long paidAt
    ) {

        if (paymentIntentId != null &&
            repo.existsByStripePaymentIntentId(paymentIntentId)) {

            System.out.println("Pago duplicado: " + paymentIntentId);
            return;
        }

        Payment pay = new Payment();

        pay.setId(UUID.randomUUID());
        pay.setUserId(UUID.fromString(userId));
        pay.setPlanCode(planCode);
        pay.setAmountCents(amount);
        pay.setCurrency(currency.toUpperCase());
        pay.setStatus("PAID");

        pay.setStripePaymentIntentId(paymentIntentId);
        pay.setStripeCheckoutSessionId(sessionId);

        LocalDateTime date = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(paidAt),
            ZoneId.systemDefault()
        );

        pay.setPaidAt(date);
        pay.setCreatedAt(date);

        repo.save(pay);

        System.out.println("✅ Pago guardado");
    }
    
	@Override
	public List<PaymentDTO> getAllPayments() {

	    return repo.findAll().stream().map(pay -> {

	        UserDTO user = null;

	        try {
	            user = authClient.getUser(pay.getUserId());
	        } catch (Exception e) {
	            System.out.println("Error obteniendo usuario: " + e.getMessage());
	        }

	        return new PaymentDTO(
	            pay.getId(),
	            user != null ? user.getFullName() : "Usuario no disponible",
	            user != null ? user.getEmail() : null,
	            user != null ? user.getPhoto() : null,
	            pay.getPlanCode(),
	            pay.getAmountCents() / 100.0,
	            pay.getStatus(),
	            pay.getCreatedAt()
	        );

	    }).toList();
	}

}