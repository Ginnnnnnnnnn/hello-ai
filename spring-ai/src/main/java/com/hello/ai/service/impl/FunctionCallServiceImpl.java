package com.hello.ai.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.hello.ai.service.FunctionCallService;
import com.hello.ai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Function Call
 *
 * @author Gin
 * @since 2026-06-22
 */
@Service
public class FunctionCallServiceImpl implements FunctionCallService, InitializingBean {

    @Autowired
    private DashScopeChatModel chatModel;

    private ChatClient chatClient;

    @Override
    public void afterPropertiesSet() {
        chatClient = ChatClient.builder(chatModel)
                .defaultTools(new TimeTools())
                .build();
    }

    @Override
    public Flux<String> chat(String message) {
        return chatClient.prompt(message).stream().content();
    }

}
