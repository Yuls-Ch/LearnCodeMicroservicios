package com.learncode_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "plans", schema = "subscription_schema")
public class Plan {

    @Id
    @GeneratedValue
    private UUID id;

    private String code;

    private String name;

    private String description;

    @Column(name = "price_cents")
    private Integer priceCents;

    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(name = "stripe_price_id")
    private String stripePriceId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public UUID getId() { return id; }

    public String getCode() { return code; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public Integer getPriceCents() { return priceCents; }

    public Integer getDurationDays() { return durationDays; }

    public String getStripePriceId() { return stripePriceId; }

    public Boolean getIsActive() { return isActive; }
}
