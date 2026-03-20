package com.learncode_backend.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.client.AuthClient;
import com.learncode_backend.repository.PlanRepository;
import com.learncode_backend.utils.ApiResponse;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    
    @Autowired
    private PlanRepository planRepo;
    
    @Autowired
    private AuthClient authClient;
    
    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<Map<String,String>>> createCheckout(
        @RequestParam String planCode,
        @AuthenticationPrincipal Jwt jwt
    ) throws Exception {

        UUID userId = authClient.getMe().getId();

        String priceId = getPriceId(planCode);

        SessionCreateParams params =
            SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("http://localhost:4200/client/home?success=true")
                .setCancelUrl("http://localhost:4200/client/plans?canceled=true")
                .putMetadata("userId", userId.toString())
                .putMetadata("planCode", planCode)
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setPrice(priceId)
                        .setQuantity(1L)
                        .build()
                )
                .build();

        Session session = Session.create(params);

        Map<String,String> data = Map.of("url", session.getUrl());

        return ResponseEntity.ok(
            new ApiResponse<>(true, "Checkout creado", data)
        );
    }

    private String getPriceId(String plan) {

        return planRepo.findByCode(plan)
            .orElseThrow(() -> new RuntimeException("Plan no existe"))
            .getStripePriceId();
    }
}

