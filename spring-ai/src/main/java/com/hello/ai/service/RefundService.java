package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * Refund
 *
 * @author Gin
 * @since 2026-06-22
 */
public interface RefundService {

    Flux<String> chat(String chatId, String message);

}
