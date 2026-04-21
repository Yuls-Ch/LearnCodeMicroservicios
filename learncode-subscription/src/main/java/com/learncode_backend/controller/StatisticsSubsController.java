package com.learncode_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learncode_backend.dto.IncomePlanDTO;
import com.learncode_backend.dto.MonthlyIncomeDTO;
import com.learncode_backend.dto.StudentPlanDTO;
import com.learncode_backend.service.StatisticsService;
import com.learncode_backend.utils.ApiResponse;

@RestController
@RequestMapping("/api/admin/statisticsSub")
public class StatisticsSubsController {

	private final StatisticsService service;

	public StatisticsSubsController(StatisticsService service) {
		this.service = service;
	}

	@GetMapping("/income-by-plan")
	public ResponseEntity<ApiResponse<List<IncomePlanDTO>>> incomePerPlan() throws Exception {

		List<IncomePlanDTO> lista = service.getIncomeByPlan();

		ApiResponse<List<IncomePlanDTO>> response = new ApiResponse<>(true, "Ingresos por plan", lista);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/students-by-plan")
	public ResponseEntity<ApiResponse<List<StudentPlanDTO>>> studentsByPlan() throws Exception {

		List<StudentPlanDTO> lista = service.getStudentByPlan();

		ApiResponse<List<StudentPlanDTO>> response = new ApiResponse<>(true, "Estudiantes por plan", lista);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/income-by-month")
	public ResponseEntity<ApiResponse<List<MonthlyIncomeDTO>>> incomeByMonth() throws Exception {

	    List<MonthlyIncomeDTO> lista = service.getIncomeByMonth();

	    ApiResponse<List<MonthlyIncomeDTO>> response =
	            new ApiResponse<>(true, "Ingresos por mes", lista);

	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	

}
