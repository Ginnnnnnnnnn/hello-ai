package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * 低层次
 *
 * @author Gin
 * @since 2026-06-18
 */
public interface SimpleService {

    String chat(String message);

    Flux<String> stream(String message);

    String memory(String message);

    String structure(String message);

    String tool(String message);

}
