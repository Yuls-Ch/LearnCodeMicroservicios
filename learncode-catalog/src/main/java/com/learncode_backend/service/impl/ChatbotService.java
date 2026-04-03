package com.learncode_backend.service.impl;

import com.learncode_backend.dto.ChatBot.ChatbotResponse;

import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import reactor.core.publisher.Mono;

@Service
public class ChatbotService {

	private final WebClient webClient;

	public ChatbotService(@Value("${n8n.webhook.url}") String n8nUrl) {
		this.webClient = WebClient.builder().baseUrl(n8nUrl)
				.clientConnector(new ReactorClientHttpConnector(HttpClient.create()
						.responseTimeout(Duration.ofSeconds(60)).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)))
				.build();
	}

	public Mono<ChatbotResponse> sendMessage(String message) {
		System.out.println("Llamando a n8n: " + message);

		return webClient.post().header("Content-Type", "application/json").header("Accept", "application/json")
				.bodyValue(Map.of("chatInput", message)).retrieve()
				.onStatus(status -> status.isError(), response -> response.bodyToMono(String.class).flatMap(body -> {
					System.err.println("n8n error: " + body);
					return Mono.error(new RuntimeException(body));
				})).bodyToMono(ChatbotResponse.class).map(res -> {
					;
					return new ChatbotResponse(res.output());
				});
	}
}