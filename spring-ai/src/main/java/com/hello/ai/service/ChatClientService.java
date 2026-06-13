package com.hello.ai.service;

import reactor.core.publisher.Flux;

public interface ChatClientService {

    String call(String message);

    Flux<String> stream(String message);

}
