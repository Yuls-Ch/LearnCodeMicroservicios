package com.learncode_backend.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateModuleDTO(
    @NotNull(message = "El ID del curso es obligatorio") UUID courseId,
    @NotBlank(message = "El título del módulo es obligatorio") String title,
    @NotNull(message = "El orden es obligatorio") Integer order
) {}