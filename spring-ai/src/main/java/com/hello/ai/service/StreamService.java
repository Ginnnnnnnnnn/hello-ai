package com.hello.ai.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

/**
 * 流式输出
 *
 * @author Gin
 * @since 2026-06-12
 */
public interface StreamService {

    String http();

    StreamingResponseBody httpStream();

    SseEmitter sse();

    Flux<String> flux();
}
