package com.hello.ai.service;

import reactor.core.publisher.Flux;

/**
 * ReAct
 *
 * @author Gin
 * @since 2026-08-12
 */
public interface ReActAgentService {

    String chat(String chatId, String msg);

    String chatAlibaba(String chatId, String msg);

    Flux<String> chatAlibabaStream(String chatId, String msg);

}
