package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * General
 *
 * @author Gin
 * @since 2026-08-12
 */
public interface GeneralAgentService {

    String chat(String chatId, String msg);

}
