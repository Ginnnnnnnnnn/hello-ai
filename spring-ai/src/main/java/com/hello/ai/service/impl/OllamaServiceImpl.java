package com.hello.ai.service.impl;

import com.hello.ai.service.OllamaService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Ollama
 *
 * @author Gin
 * @since 2026-06-18
 */
@Service
public class OllamaServiceImpl implements OllamaService, InitializingBean {

    @Autowired
    private OllamaChatModel chatModel;

    private ChatClient chatClient;

    @Override
    public void afterPropertiesSet() {
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Override
    public Flux<String> chat(String message) {
        return chatClient.prompt(new Prompt(
                        new UserMessage(message)
                ))
                .stream()
                .content();
    }

}
