package com.learncode_backend.dto;

import java.util.UUID;

public record CourseProgressDTO( 
	UUID courseId,
	String title,
	String iconUrl,
	int totalModules,
	int completedModules,
	int progressPercentage
	) {}
