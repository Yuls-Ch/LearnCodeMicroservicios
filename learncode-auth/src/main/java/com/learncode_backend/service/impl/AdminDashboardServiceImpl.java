package com.learncode_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learncode_backend.client.CatalogClient;
import com.learncode_backend.client.SubscriptionClient;
import com.learncode_backend.dto.AdminDashboardDTO;
import com.learncode_backend.repository.UserRepository;
import com.learncode_backend.service.AdminDashboardService;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
	
	@Autowired
    CatalogClient catalog;

    @Autowired
    SubscriptionClient subscription;
	
	@Autowired
	UserRepository userRepo;

	@Override
	public AdminDashboardDTO getDashboard() {
		return new AdminDashboardDTO(
			catalog.countCourses(),
            userRepo.countByRole("USER"),
            subscription.dailyIncome()
        );
	}
}
