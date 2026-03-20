package com.learncode_backend.dto;

import java.util.UUID;

// Usamos este DTO para listar (sin el contenido pesado Base64)
public record FileDTO(
    UUID id,
    String fileName,
    String mimeType
) {}