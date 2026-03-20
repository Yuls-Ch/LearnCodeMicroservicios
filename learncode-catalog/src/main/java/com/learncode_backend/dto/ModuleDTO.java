package com.learncode_backend.dto;

import java.util.List;
import java.util.UUID;

public record ModuleDTO(
    UUID id,
    Integer order,
    String title,
    List<FileDTO> files
) {}