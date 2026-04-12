package com.learncode_backend.service.chatbot;

import com.learncode_backend.model.ChatMessageEntity;
import com.learncode_backend.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatHistoryService {

    private final ChatMessageRepository repository;

    public ChatHistoryService(ChatMessageRepository repository) {
        this.repository = repository;
    }

    public List<ChatMessageEntity> getHistory(String sessionId) {
        return repository.findBySessionIdOrderByCreatedAtAsc(sessionId);
    }

    public void save(String sessionId, String userMsg, String assistantMsg) {
        repository.save(new ChatMessageEntity(sessionId, "user", userMsg));
        repository.save(new ChatMessageEntity(sessionId, "model", assistantMsg));
    }
}