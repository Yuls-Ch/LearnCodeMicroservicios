package com.learncode_backend.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.learncode_backend.dto.PlanDTO;
import com.learncode_backend.model.Plan;
import com.learncode_backend.service.PlanService;
import com.learncode_backend.utils.ApiResponse;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

	@Autowired
    private PlanService service;
	@Autowired
    private ModelMapper mapper;


    @GetMapping
    public ResponseEntity<ApiResponse<?>> listPlans() throws Exception {

        List<Plan> plans = service.getActivePlans();

        List<PlanDTO> dtoList = plans.stream()
                .map(p -> {
                    PlanDTO dto = mapper.map(p, PlanDTO.class);
                    dto.setPrice(p.getPriceCents() / 100.0);
                    return dto;
                })
                .toList();

        ApiResponse<List<PlanDTO>> response =
                new ApiResponse<>(true, "Lista de planes activos", dtoList);

        return ResponseEntity.ok(response);
    }
}