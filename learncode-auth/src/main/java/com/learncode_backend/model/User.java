package com.learncode_backend.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users", schema = "auth_schema")
@Data
public class User {
    @Id
    private UUID id;

    @Column(name = "google_sub", unique = true)
    private String googleSub;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "full_name")
    private String fullName;
    
    private String photo;
    private String role;
    private String status;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
