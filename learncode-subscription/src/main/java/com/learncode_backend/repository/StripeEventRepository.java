package com.learncode_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learncode_backend.model.StripeEvent;

public interface StripeEventRepository
extends JpaRepository<StripeEvent, String> {
}
