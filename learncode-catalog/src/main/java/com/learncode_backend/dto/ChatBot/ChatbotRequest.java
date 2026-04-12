package com.learncode_backend.dto.ChatBot;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChatbotRequest( 
		@JsonProperty("chatInput") String chatInput,
		@JsonProperty("sessionId") String sessionId
		) {}
