package com.hello.ai.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.hello.ai.service.RefundService;
import com.hello.ai.tools.OrderTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Refund
 *
 * @author Gin
 * @since 2026-06-22
 */
@Service
public class RefundServiceImpl implements RefundService, InitializingBean {

    @Autowired
    private DashScopeChatModel chatModel;

    @Value("classpath:templates/refund-system-prompt.md")
    private Resource resource;

    private ChatClient chatClient;

    @Override
    public void afterPropertiesSet() {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(100)
                .build();
        chatClient = ChatClient.builder(chatModel)
                .defaultSystem(resource)
                .defaultTools(new OrderTools())
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    @Override
    public Flux<String> chat(String chatId, String message) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                用户信息:
                - 用户ID:{userId}
                """);
        promptTemplate.add("userId", "USER0000001");
        return chatClient.prompt(new Prompt(
                        new SystemMessage(promptTemplate.render()),
                        new UserMessage(message)
                ))
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }

}
