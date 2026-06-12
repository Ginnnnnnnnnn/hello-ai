package com.hello.ai.service;

import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * ChatModel
 *
 * @author Gin
 * @since 2026-06-12
 */
public interface ChatModelService {

    String callString(String message);

    String callMessage(String message);

    String callPrompt(String message);

    Flux<String> streamString(String message);

    Flux<String> streamMessage(String message);

    Flux<ChatResponse> streamPrompt(String message);

}
