package com.learncode_backend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments", schema = "subscription_schema")
public class Payment {
	@Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "plan_code", length = 50)
    private String planCode;

    @Column(name = "amount_cents")
    private Integer amountCents;

    @Column(length = 10)
    private String currency;

    @Column(length = 20)
    private String status;

    @Column(name = "stripe_checkout_session_id", length = 150, unique = true)
    private String stripeCheckoutSessionId;

    @Column(name = "stripe_payment_intent_id", length = 150)
    private String stripePaymentIntentId;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Integer getAmountCents() {
		return amountCents;
	}

	public void setAmountCents(Integer amountCents) {
		this.amountCents = amountCents;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStripeCheckoutSessionId() {
		return stripeCheckoutSessionId;
	}

	public void setStripeCheckoutSessionId(String stripeCheckoutSessionId) {
		this.stripeCheckoutSessionId = stripeCheckoutSessionId;
	}

	public String getStripePaymentIntentId() {
		return stripePaymentIntentId;
	}

	public void setStripePaymentIntentId(String stripePaymentIntentId) {
		this.stripePaymentIntentId = stripePaymentIntentId;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
