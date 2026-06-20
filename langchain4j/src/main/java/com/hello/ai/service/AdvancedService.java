package com.hello.ai.service;

import reactor.core.publisher.Flux;

public interface AdvancedService {

    String chat(String message);

    Flux<String> stream(String message);

    String structure(String message);

    Flux<String> chatMemory(String chatId, String message);

    Flux<String> tool(String message);

}
