package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * Multimodal
 *
 * @author Gin
 * @since 2026-08-10
 */
public interface MediaService {

    Flux<String> call(String url, String msg);

}
