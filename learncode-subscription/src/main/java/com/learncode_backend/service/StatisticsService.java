package com.learncode_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learncode_backend.dto.IncomePlanDTO;
import com.learncode_backend.dto.MonthlyIncomeDTO;
import com.learncode_backend.dto.StudentPlanDTO;
import com.learncode_backend.repository.PaymentRepository;
import com.learncode_backend.repository.SubscriptionRepository;

@Service
public class StatisticsService {

	private final PaymentRepository paymentRepository;
	private final SubscriptionRepository subscriptionRepository;

	public StatisticsService(PaymentRepository paymentRepository, SubscriptionRepository subscriptionRepository) {
		this.paymentRepository = paymentRepository;
		this.subscriptionRepository = subscriptionRepository;
	}

	// Estudiante x Plan
	public List<StudentPlanDTO> getStudentByPlan() {
		return subscriptionRepository.studentsByPlan();
	}

	// Ingreso x Plan
	public List<IncomePlanDTO> getIncomeByPlan() {
		return paymentRepository.incomeByPlan();
	}

	// Ingresos x Mes
	public List<MonthlyIncomeDTO> getIncomeByMonth() {
		return paymentRepository.incomeByMonth();
	}

}
