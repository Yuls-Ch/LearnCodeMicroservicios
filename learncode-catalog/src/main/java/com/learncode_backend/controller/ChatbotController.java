package com.learncode_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import com.learncode_backend.dto.ChatBot.ChatbotRequest;
import com.learncode_backend.dto.ChatBot.ChatbotResponse;
import com.learncode_backend.service.impl.ChatbotService;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }
    
    @PostMapping
    public Mono<ResponseEntity<ChatbotResponse>> chat(
            @RequestBody ChatbotRequest request,
            @RequestHeader("Authorization") String token) {
        
        System.out.println("Request recibido: " + request);
        System.out.println("Message: " + request.chatInput());
        
        return chatbotService.sendMessage(request.chatInput())
                .map(ResponseEntity::ok)
                .doOnError(e -> System.err.println("Error en controller: " + e.getMessage()))
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }
    
}