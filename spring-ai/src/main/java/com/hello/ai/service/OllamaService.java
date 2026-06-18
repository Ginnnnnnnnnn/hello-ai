package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * Ollama
 *
 * @author Gin
 * @since 2026-06-18
 */
public interface OllamaService {

    Flux<String> chat(String message);

}
