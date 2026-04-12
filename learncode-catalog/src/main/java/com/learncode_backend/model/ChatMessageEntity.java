package com.learncode_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_history", schema = "catalog_schema")
public class ChatMessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "session_id")
	private String sessionId;

	private String role; // "user" o "model"

	@Column(columnDefinition = "text")
	private String content;

	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	public ChatMessageEntity() {
	}

	public ChatMessageEntity(String sessionId, String role, String content) {
		this.sessionId = sessionId;
		this.role = role;
		this.content = content;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getRole() {
		return role;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}