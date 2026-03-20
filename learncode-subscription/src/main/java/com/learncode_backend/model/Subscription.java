package com.learncode_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions", schema = "subscription_schema")
public class Subscription {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "plan_code", nullable = false)
    private String planCode; 

    @Column(nullable = false)
    private String status; 

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = true)
    private LocalDateTime endAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
