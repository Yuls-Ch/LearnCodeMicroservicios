package com.learncode_backend.service.chatbot;

import com.learncode_backend.dto.ChatBot.ChatbotResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ChatbotService {

    private final WebClient webClient;
    private final ChatHistoryService historyService;

    public ChatbotService(WebClient.Builder webClientBuilder,
                          ChatHistoryService historyService) {

        this.webClient = webClientBuilder.build();
        this.historyService = historyService;
    }

    public Mono<ChatbotResponse> sendMessage(String message, String sessionId) {

        return webClient.post()
                .uri("http://localhost:5678/webhook/ChatBot")
                .bodyValue(Map.of("chatInput", message))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {

                    String output = (String) response.get("response");

                    historyService.save(sessionId, message, output);

                    return new ChatbotResponse(output);
                });
    }
}