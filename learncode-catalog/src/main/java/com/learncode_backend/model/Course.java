package com.learncode_backend.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import java.sql.Types;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses", schema = "catalog_schema")
public class Course {

	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@Column(nullable = false, length = 150)
	@JdbcTypeCode(Types.VARCHAR)
	private String title;

	@Column(length = 150)
	private String subtitle;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "icon_url", columnDefinition = "TEXT")
	private String iconUrl;

	@Column(name = "cover_url", columnDefinition = "TEXT")
	private String coverUrl;

	@Column(name = "is_free", nullable = false)
	private boolean free;

	@Column(name = "required_plan_code", length = 50)
	private String requiredPlanCode;

	@Column(name = "is_published", nullable = false)
	private boolean published;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	private List<CourseModule> modules;



	public Course() {
	}

	public Course(UUID id, String title, String subtitle, String description, String iconUrl, String coverUrl,
			boolean free, String requiredPlanCode, boolean published, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.description = description;
		this.iconUrl = iconUrl;
		this.coverUrl = coverUrl;
		this.free = free;
		this.requiredPlanCode = requiredPlanCode;
		this.published = published;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public boolean getFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public String getRequiredPlanCode() {
		return requiredPlanCode;
	}

	public void setRequiredPlanCode(String requiredPlanCode) {
		this.requiredPlanCode = requiredPlanCode;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}

}
