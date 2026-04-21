package com.learncode_backend.dto;

public class MonthlyIncomeDTO {
	private Integer month;
	private Double totalIncome;

	public MonthlyIncomeDTO(Integer month, Double totalIncome) {
		this.month = month.intValue();
		this.totalIncome = totalIncome;
	}

	public Integer getMonth() {
		return month;
	}

	public Double getTotalIncome() {
		return totalIncome;
	}
}
