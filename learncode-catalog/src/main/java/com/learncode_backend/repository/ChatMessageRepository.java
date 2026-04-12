package com.learncode_backend.repository;

import com.learncode_backend.model.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, String> {
	List<ChatMessageEntity> findBySessionIdOrderByCreatedAtAsc(String sessionId);
}