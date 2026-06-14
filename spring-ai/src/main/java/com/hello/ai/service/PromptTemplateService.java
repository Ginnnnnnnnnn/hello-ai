package com.hello.ai.service;

import reactor.core.publisher.Flux;

public interface PromptTemplateService {

    Flux<String> template(String message);

    Flux<String> file(String message);

}
