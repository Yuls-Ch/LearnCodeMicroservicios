package com.learncode_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionDTO {

    @NotNull(message = "El id no puede ser null")
    private UUID id;

    @NotBlank(message = "El código del plan es obligatorio")
    private String planCode;

    @NotBlank(message = "El estado es obligatorio")
    private String status;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime startAt;

    private LocalDateTime endAt; 
}
