package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * modular
 *
 * @author Gin
 * @since 2026-08-06
 */
public interface ModularService {

    Flux<String> chat(String query);

}
