package com.learncode_backend.dto;

import java.util.UUID;

public record ClientCourseDTO(
	 UUID id,
     String title,
     String subtitle,
     String iconUrl,
     String coverUrl,
     boolean isFree,
     String requiredPlanCode,
     
     Long modulesCount, 
     Long filesCount
){}
