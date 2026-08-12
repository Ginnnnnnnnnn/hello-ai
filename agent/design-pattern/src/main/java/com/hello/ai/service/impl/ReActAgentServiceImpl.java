package com.hello.ai.service.impl;

import com.hello.ai.service.ReActAgentService;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ReAct
 *
 * @author Gin
 * @since 2026-08-12
 */
@Service
public class ReActAgentServiceImpl implements ReActAgentService {

    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private ChatModel chatModel;

    @Override
    public String chat(String chatId, String msg) {
        return "";
    }

}
