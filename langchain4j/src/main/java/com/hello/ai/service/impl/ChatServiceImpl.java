package com.hello.ai.service.impl;

import com.hello.ai.service.ChatService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Chat
 *
 * @author Gin
 * @since 2026-06-18
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    OpenAiChatModel chatModel;

    @Override
    public String message(String message) {
        return chatModel.chat(message);
    }

}
