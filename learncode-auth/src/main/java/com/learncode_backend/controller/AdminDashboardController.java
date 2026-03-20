package com.learncode_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.dto.AdminDashboardDTO;
import com.learncode_backend.service.AdminDashboardService;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {
	@Autowired
	AdminDashboardService service;

    @GetMapping
    public AdminDashboardDTO dashboard() {
        return service.getDashboard();
    }
}
