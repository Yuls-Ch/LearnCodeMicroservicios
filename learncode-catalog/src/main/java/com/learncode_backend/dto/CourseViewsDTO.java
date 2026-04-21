package com.learncode_backend.dto;

public class CourseViewsDTO {
	private String title;
	private Long totalViews;

	public CourseViewsDTO(String title, Long totalViews) {
        this.title = title;
        this.totalViews = totalViews;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(Long totalViews) {
		this.totalViews = totalViews;
	}
	
	

}
