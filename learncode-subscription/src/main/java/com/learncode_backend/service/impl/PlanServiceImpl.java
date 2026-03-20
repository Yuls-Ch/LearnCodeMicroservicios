package com.learncode_backend.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.learncode_backend.model.Plan;
import com.learncode_backend.repository.PlanRepository;
import com.learncode_backend.service.PlanService;

@Service
public class PlanServiceImpl
        extends ICRUDImpl<Plan, UUID>
        implements PlanService {

    @Autowired
    private PlanRepository repository;

    @Override
    public JpaRepository<Plan, UUID> getRepository() {
        return repository;
    }

    @Override
    public List<Plan> getActivePlans() throws Exception {
        return repository.findByIsActiveTrue();
    }

    @Override
    public Plan findByCode(String code) throws Exception {
        return repository.findByCode(code)
                .orElse(null);
    }
}