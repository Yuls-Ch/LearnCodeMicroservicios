package com.learncode_backend.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class UserDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String photo;
    private String role;
}