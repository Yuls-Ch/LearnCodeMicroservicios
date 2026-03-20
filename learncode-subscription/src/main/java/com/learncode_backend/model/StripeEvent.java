package com.learncode_backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stripe_events", schema = "subscription_schema")
public class StripeEvent {

    @Id
    private String id;

    @Column(name = "received_at")
    private LocalDateTime receivedAt = LocalDateTime.now();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(LocalDateTime receivedAt) {
		this.receivedAt = receivedAt;
	}

    // getters / setters
    
    
}
