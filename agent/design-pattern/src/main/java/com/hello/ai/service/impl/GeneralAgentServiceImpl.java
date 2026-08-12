package com.hello.ai.service.impl;

import com.hello.ai.service.GeneralAgentService;
import com.hello.ai.tools.DateTimeTools;
import com.hello.ai.tools.WeatherTools;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * General
 *
 * @author Gin
 * @since 2026-08-12
 */
@Service
public class GeneralAgentServiceImpl implements GeneralAgentService {

    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private ChatModel chatModel;

    @Override
    public String chat(String chatId, String msg) {
        // 加入记忆
        chatMemory.add(chatId, new UserMessage(msg));
        // 排版消息
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("""
                你是一个智能助手，你擅长使用工具帮我解决问题。
                """));
        messages.addAll(chatMemory.get(chatId));
        // 定义提示词
        Prompt prompt = new Prompt(
                messages,
                ToolCallingChatOptions.builder()
                        //指定工具
                        .toolCallbacks(ToolCallbacks.from(new DateTimeTools(), new WeatherTools()))
                        .model("deepseek-v3")
                        .build()
        );
        // 调用大模型
        return chatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();
    }

}
