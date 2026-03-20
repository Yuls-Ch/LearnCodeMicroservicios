package com.learncode_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminCourseDTO {
	
	private UUID id;

	@NotBlank(message = "El título es obligatorio")
	@Size(min = 5, max = 150, message = "El título debe tener mínimo 5 caracteres")
	private String title;

	@NotBlank(message = "El subtítulo es obligatorio")
	@Size(min = 5, max = 150, message = "El subtítulo debe tener mínimo 5 caracteres")
	private String subtitle;

	@NotBlank(message = "La descripción es obligatoria")
	@Size(min = 20, message = "La descripción debe tener mínimo 20 caracteres")
	private String description;

	@NotBlank(message = "El icono del curso es obligatorio")
	private String iconUrl;

	@NotBlank(message = "El color cover es obligatorio")
	private String coverUrl;

	@JsonProperty("free")
	private boolean isFree;

	private String requiredPlanCode;

	@JsonProperty("isPublished")
	private boolean published;

	private LocalDateTime createdAt;

	public AdminCourseDTO() {
	}

	public AdminCourseDTO(UUID id,
			@NotBlank(message = "El título es obligatorio") @Size(min = 5, max = 150, message = "El título debe tener mínimo 5 caracteres") String title,
			@NotBlank(message = "El subtítulo es obligatorio") @Size(min = 5, max = 150, message = "El subtítulo debe tener mínimo 5 caracteres") String subtitle,
			@NotBlank(message = "La descripción es obligatoria") @Size(min = 20, message = "La descripción debe tener mínimo 20 caracteres") String description,
			@NotBlank(message = "El icono del curso es obligatorio") String iconUrl,
			@NotBlank(message = "El color cover es obligatorio") String coverUrl, boolean isFree,
			String requiredPlanCode, boolean published, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.description = description;
		this.iconUrl = iconUrl;
		this.coverUrl = coverUrl;
		this.isFree = isFree;
		this.requiredPlanCode = requiredPlanCode;
		this.published = published;
		this.createdAt = createdAt;
	}

	public UUID getId() { return id; }
	public void setId(UUID id) { this.id = id; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getSubtitle() { return subtitle; }
	public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getIconUrl() { return iconUrl; }
	public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

	public String getCoverUrl() { return coverUrl; }
	public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

	public boolean isFree() { return isFree; }
	public void setFree(boolean isFree) { this.isFree = isFree; }

	public String getRequiredPlanCode() { return requiredPlanCode; }
	public void setRequiredPlanCode(String requiredPlanCode) { this.requiredPlanCode = requiredPlanCode; }

	public boolean isPublished() { return published; }
	public void setPublished(boolean published) { this.published = published; }

	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
	
	@AssertTrue(message = "Si el curso no es gratis, debe seleccionar un plan")
	public boolean isPlanValid() {
		if (!isFree) {
			return requiredPlanCode != null && requiredPlanCode.matches("ORO|PLATINO|DIAMANTE");
		}
		return true;
	}

	@AssertTrue(message = "Un curso gratis no debe tener plan requerido")
	public boolean isFreeCourseValid() {
		if (isFree) {
			return requiredPlanCode == null || requiredPlanCode.isBlank() || requiredPlanCode.equals("FREE");
		}
		return true;
	}

}