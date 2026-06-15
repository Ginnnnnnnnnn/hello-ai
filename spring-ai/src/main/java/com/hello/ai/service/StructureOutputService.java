package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * StructureOutput
 *
 * @author Gin
 * @since 2026-06-15
 */
public interface StructureOutputService {

    Flux<String> object(String message);

    String anime(String message);

    String animeList(String message);

}
