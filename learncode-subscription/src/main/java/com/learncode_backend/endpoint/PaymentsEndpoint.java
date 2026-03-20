package com.learncode_backend.endpoint;

import java.util.List;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import com.learncode.soap.payments.GetPaymentsRequest;
import com.learncode.soap.payments.GetPaymentsResponse;
import com.learncode.soap.payments.Payment;

import com.learncode_backend.dto.PaymentDTO;
import com.learncode_backend.service.PaymentService;
@Endpoint
public class PaymentsEndpoint {

    private static final String NAMESPACE_URI =
            "http://learncode.com/soap/payments";

    private final PaymentService paymentService;

    public PaymentsEndpoint(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPaymentsRequest")
    @ResponsePayload
    public GetPaymentsResponse getPayments(
            @RequestPayload GetPaymentsRequest request) {

        GetPaymentsResponse response = new GetPaymentsResponse();

        List<PaymentDTO> lista = paymentService.getAllPayments();

        for (PaymentDTO dto : lista) {

            Payment soapPayment = new Payment();

            soapPayment.setEmail(dto.getEmail());
            soapPayment.setFoto(dto.getPhoto());
            soapPayment.setPlan(dto.getPlanCode());
            soapPayment.setMonto(
                    java.math.BigDecimal.valueOf(dto.getAmount()));
            soapPayment.setEstado(dto.getStatus());

            response.getPayments().add(soapPayment);
        }

        return response;
    }
}