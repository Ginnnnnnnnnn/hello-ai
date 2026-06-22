package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * Function Call
 *
 * @author Gin
 * @since 2026-06-22
 */
public interface FunctionCallService {

    Flux<String> chat(String message);

}
