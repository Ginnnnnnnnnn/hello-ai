package com.hello.ai.service.impl;

import com.hello.ai.service.ChatMemoryService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * ChatMemory
 *
 * @author Gin
 * @since 2026-06-17
 */
@Service
public class ChatMemoryServiceImpl implements ChatMemoryService, InitializingBean {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private ChatMemory chatMemory;

    private ChatClient chatClient;

    private ChatClient chatMemoryClient;

    List<Message> messages = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
//        ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(100).build();
        this.chatMemoryClient = ChatClient
                .builder(chatModel)
                .defaultSystem("你是个傲娇小萝莉")
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();
        messages.add(new SystemMessage("你是个可爱小萝莉"));
    }

    @Override
    public Flux<String> messages(String message) {
        messages.add(new UserMessage(message));
        StringBuilder assistantContent = new StringBuilder();
        Flux<String> content = chatClient.prompt(new Prompt(messages))
                .stream()
                .content();
        return content
                .doOnNext(assistantContent::append) // 把每个元素拼接在一起
                .doOnComplete(() -> messages.add(new AssistantMessage(assistantContent.toString()))); // 响应结束加入对话
    }

    @Override
    public Flux<String> chatId(String chatId, String message) {
        return chatMemoryClient.prompt(new Prompt(
                        new UserMessage(message)
                ))
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }

}
