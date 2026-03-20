package com.learncode_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GestionClienteDTO {
    private UUID id;
    private String email;
	
	@NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String fullName;
	
    private String photo;
    
    @NotBlank(message = "El rol es obligatorio")
    @Pattern(
        regexp = "USER|ADMIN",
        message = "El rol debe ser USER o ADMIN"
    )
    private String role;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
        regexp = "ACTIVE|BLOCKED",
        message = "Estado inválido debe ser ACTIVE o BLOCKED "
    )
    private String status;
    
    private LocalDateTime createdAt;
}
