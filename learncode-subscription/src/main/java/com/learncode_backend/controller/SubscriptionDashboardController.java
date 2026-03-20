package com.learncode_backend.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.repository.PaymentRepository;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionDashboardController {

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/daily-income")
    public BigDecimal dailyIncome() {
        return paymentRepository.dailyIncome();
    }
}

