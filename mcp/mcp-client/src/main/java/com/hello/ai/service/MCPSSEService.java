package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * MCP SSE
 *
 * @author Gin
 * @since 2026-06-29
 */
public interface MCPSSEService {

    Flux<String> chat(String message);

}
