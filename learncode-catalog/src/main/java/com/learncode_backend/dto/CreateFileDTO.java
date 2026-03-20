package com.learncode_backend.dto;

import java.util.UUID;

// Este DTO es para recibir el archivo desde el front (con contenido)
public record CreateFileDTO(
    UUID moduleId,
    String fileName,
    String base64, // El contenido pesado
    String mimeType
) {}