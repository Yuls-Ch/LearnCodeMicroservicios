package com.learncode_backend.dto;

public class IncomePlanDTO {
	private String plan;
	private Double totalIncome;

	public IncomePlanDTO(String plan, Double totalIncome) {
	        this.plan = plan;
	        this.totalIncome = totalIncome;
	    }

	public String getPlan() {
		return plan;
	}

	public Double getTotalIncome() {
		return totalIncome;
	}
}
