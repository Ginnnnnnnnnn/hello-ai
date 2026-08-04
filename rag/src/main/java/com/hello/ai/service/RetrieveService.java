package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * retrieve
 *
 * @author Gin
 * @since 2026-08-04
 */
public interface RetrieveService {

    Flux<String> call(String query);

}
