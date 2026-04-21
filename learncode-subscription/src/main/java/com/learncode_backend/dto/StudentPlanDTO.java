package com.learncode_backend.dto;

public class StudentPlanDTO {
	private String plan;
	private Long totalStudents;

	public StudentPlanDTO(String plan, Long totalStudents) {
	        this.plan = plan;
	        this.totalStudents = totalStudents;
	    }

	public String getPlan() {
		return plan;
	}

	public Long getTotalStudents() {
		return totalStudents;
	}
}
