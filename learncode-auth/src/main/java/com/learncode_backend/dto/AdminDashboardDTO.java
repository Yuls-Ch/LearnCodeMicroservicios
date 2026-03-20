package com.learncode_backend.dto;

import java.math.BigDecimal;
import lombok.*;

@Data
@AllArgsConstructor
public class AdminDashboardDTO{
	private long totalCourses;
    private long totalUsers;
    private BigDecimal dailyIncome;
}