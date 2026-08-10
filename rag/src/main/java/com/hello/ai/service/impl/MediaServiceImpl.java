package com.hello.ai.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.hello.ai.service.MediaService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Multimodal
 *
 * @author Gin
 * @since 2026-08-10
 */
@Service
public class MediaServiceImpl implements MediaService, InitializingBean {

    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Override
    public Flux<String> call(String url, String msg) {
        return chatClient.prompt(new Prompt(
                        UserMessage.builder()
                                .text(msg)
                                .media(Media.builder().mimeType(Media.Format.IMAGE_PNG).data(url).build())
                                .build(),
                        DashScopeChatOptions.builder()
                                .model("qwen3-vl-plus")
                                .multiModel(true)
                                .build()
                ))
                .stream()
                .content();
    }

}
