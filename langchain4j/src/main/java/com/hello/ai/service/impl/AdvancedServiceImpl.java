package com.hello.ai.service.impl;

import com.hello.ai.config.MapChatMemoryStore;
import com.hello.ai.service.AdvancedService;
import com.hello.ai.service.ai.LangChainAiService;
import com.hello.ai.service.ai.LangChainMemoryAiService;
import com.hello.ai.structure.AnimeStructure;
import com.hello.ai.tools.TemperatureTools;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AdvancedServiceImpl implements AdvancedService, InitializingBean {

    @Autowired
    private LangChainAiService langChainAiService;

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private StreamingChatModel streamingChatModel;

    @Autowired
    private MapChatMemoryStore mapChatMemoryStore;

    private LangChainMemoryAiService langChainMemoryAiService;

    @Override
    public void afterPropertiesSet() {
        langChainMemoryAiService = AiServices.builder(LangChainMemoryAiService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(100)
                        .chatMemoryStore(mapChatMemoryStore)
                        .build()
                )
                .tools(new TemperatureTools())
                .build();
    }

    @Override
    public String chat(String message) {
        return langChainAiService.chat(message);
    }

    @Override
    public Flux<String> stream(String message) {
        return langChainAiService.chatStream(message);
    }

    @Override
    public String structure(String message) {
        AnimeStructure anime = langChainAiService.anime(message);
        return anime.toString();
    }

    @Override
    public Flux<String> chatMemory(String chatId, String message) {
        return langChainMemoryAiService.chatStreamMemory(chatId, message);
    }

    @Override
    public Flux<String> tool(String message) {
        return langChainMemoryAiService.chatStream(message);
    }

}
