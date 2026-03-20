package com.learncode_backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PlanDTO {

    @NotBlank(message = "El código es obligatorio")
    private String code;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String description;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    private Double price;

    @NotNull(message = "La duración es obligatoria")
    @Positive(message = "La duración debe ser mayor a 0")
    private Integer durationDays;
}