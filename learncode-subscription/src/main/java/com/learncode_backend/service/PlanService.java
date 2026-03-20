package com.learncode_backend.service;

import java.util.List;
import java.util.UUID;

import com.learncode_backend.model.Plan;

public interface PlanService extends ICRUD<Plan, UUID> {

    List<Plan> getActivePlans() throws Exception;

    Plan findByCode(String code) throws Exception;
}