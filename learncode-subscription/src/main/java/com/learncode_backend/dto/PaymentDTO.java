package com.learncode_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private UUID id;
    private String fullName;
    private String email;
    private String photo;
    private String planCode;
    private Double amount;
    private String status;
    private LocalDateTime createdAt;

}
