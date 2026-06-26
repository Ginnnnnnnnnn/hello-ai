package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * MCP Client
 *
 * @author Gin
 * @since 2026-06-26
 */
public interface MCPClientService {

    Object tools(String type);

    Flux<String> call(String message);

}
