package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * ChatMemory
 *
 * @author Gin
 * @since 2026-06-17
 */
public interface ChatMemoryService {

    Flux<String> messages(String message);

    Flux<String> chatId(String chatId, String message);

}
