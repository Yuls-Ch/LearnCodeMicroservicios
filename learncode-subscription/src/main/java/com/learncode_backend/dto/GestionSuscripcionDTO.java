package com.learncode_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class GestionSuscripcionDTO {
	private UUID id;
    private String fullName;
	private String email;
    private String photo;

	@NotBlank(message = "El plan no puede estar vacío")
	@Pattern(
		regexp = "FREE|ORO|DIAMANTE|PLATINO",
		message = "El plan debe ser FREE, ORO, DIAMANTE, PLATINO"
	)
    private String plan;
	
    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(
    	regexp = "ACTIVE|CANCELED|EXPIRED",
    	message = "El estado debe ser ACTIVE, CANCELED, EXPIRED"
    )
    private String status;
    
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime updatedAt;
}