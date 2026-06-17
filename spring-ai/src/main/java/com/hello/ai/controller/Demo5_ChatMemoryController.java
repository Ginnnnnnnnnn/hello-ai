package com.hello.ai.controller;

import com.hello.ai.service.ChatMemoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * ChatMemory
 *
 * @author Gin
 * @since 2026-06-17
 */
@RestController
@RequestMapping("/chat/memory")
public class Demo5_ChatMemoryController {

    @Autowired
    private ChatMemoryService chatMemoryService;

    @GetMapping("/messages")
    public Flux<String> messages(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return chatMemoryService.messages(message);
    }

    @GetMapping("/chatId")
    public Flux<String> chatId(@RequestParam String chatId, @RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return chatMemoryService.chatId(chatId, message);
    }

    @GetMapping("/repository")
    public Flux<String> repository(@RequestParam String chatId, @RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return chatMemoryService.repository(chatId, message);
    }

}
