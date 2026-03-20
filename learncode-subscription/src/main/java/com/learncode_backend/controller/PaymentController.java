package com.learncode_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.dto.PaymentDTO;
import com.learncode_backend.model.Payment;
import com.learncode_backend.service.PaymentService;
import com.learncode_backend.utils.ApiResponse;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ApiResponse<List<PaymentDTO>> getPayments() {

        List<PaymentDTO> payments = paymentService.getAllPayments();

        return new ApiResponse<>(
                true,
                "Lista de pagos obtenida correctamente",
                payments
        );
    }




}
