package com.hello.ai.service.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface LangChainMemoryAiService {

    Flux<String> chatStream(String userMessage);

    String chatMemory(@MemoryId String memoryId, @UserMessage String userMessage);

    Flux<String> chatStreamMemory(@MemoryId String memoryId, @UserMessage String userMessage);

}