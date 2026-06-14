package com.hello.ai.service;

import reactor.core.publisher.Flux;

public interface PromptService {

    Flux<String> role(String message);

    Flux<String> shot(String message);

    Flux<String> chat(String message);

}
