package com.learncode_backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learncode_backend.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, UUID> {

    Optional<Plan> findByCode(String code);

    List<Plan> findByIsActiveTrue();
}