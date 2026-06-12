package com.hello.ai.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.hello.ai.service.ChatModelService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * ChatModel
 *
 * @author Gin
 * @since 2026-06-12
 */
@Service
public class ChatModelServiceImpl implements ChatModelService {

    @Autowired
    private DashScopeChatModel chatModel;

    @Override
    public String callString(String message) {
        return chatModel.call(message);
    }

    @Override
    public String callMessage(String message) {
        Message systemMessage = new SystemMessage("你是一个翻译助手，负责把用户的消息翻译成英文");
        Message userMessage = new UserMessage(message);
        return chatModel.call(systemMessage, userMessage);
    }

    @Override
    public String callPrompt(String message) {
        Message systemMessage = new SystemMessage("你的名字叫Doro");
        Message userMessage = new UserMessage(message);
        ChatOptions chatOptions = ChatOptions.builder()
                .model("deepseek-v3").build();
        Prompt prompt = new Prompt.Builder()
                .messages(systemMessage, userMessage)
                .chatOptions(chatOptions)
                .build();
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getText();
    }

    @Override
    public Flux<String> streamString(String message) {
        return chatModel.stream(message);
    }

    @Override
    public Flux<String> streamMessage(String message) {
        Message systemMessage = new SystemMessage("你是一个翻译助手，负责把用户的消息翻译成英文");
        Message userMessage = new UserMessage(message);
        return chatModel.stream(systemMessage, userMessage);
    }

    @Override
    public Flux<ChatResponse> streamPrompt(String message) {
        Message systemMessage = new SystemMessage("你的名字叫Doro");
        Message userMessage = new UserMessage(message);
        ChatOptions chatOptions = ChatOptions.builder()
                .model("deepseek-v3").build();
        Prompt prompt = new Prompt.Builder()
                .messages(systemMessage, userMessage)
                .chatOptions(chatOptions)
                .build();
        return chatModel.stream(prompt);
    }

}
