package com.hello.ai.controller;

import com.hello.ai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chat
 *
 * @author Gin
 * @since 2026-06-18
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping("/message")
    public String message(@RequestParam String message) {
        return chatService.message(message);
    }

}
