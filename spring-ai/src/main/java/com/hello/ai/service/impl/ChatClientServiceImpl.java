package com.hello.ai.service.impl;

import com.hello.ai.service.ChatClientService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatClientServiceImpl implements ChatClientService {

    @Autowired
    private ChatClient chatClient;

    @Override
    public String call(String message) {
        return chatClient.prompt(new Prompt(
                        new SystemMessage("结尾必须有一个emoji"),
                        new UserMessage(message)
                ))
                .call()
                .content();
    }

    @Override
    public Flux<String> stream(String message) {
        return chatClient.prompt()
                // 这种调用方式会覆盖，只会有一个生效
                .system("你的名字叫Doro")
                .user(message)
                .stream()
                .content();
    }

}
