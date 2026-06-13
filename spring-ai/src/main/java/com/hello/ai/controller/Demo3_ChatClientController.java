package com.hello.ai.controller;

import com.hello.ai.service.ChatClientService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * ChatClient
 *
 * @author Gin
 * @since 2026-06-13
 */
@RestController
@RequestMapping("/chatClient")
public class Demo3_ChatClientController {

    @Autowired
    private ChatClientService chatClientService;

    @GetMapping("/call")
    public String call(@RequestParam String message) {
        return chatClientService.call(message);
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return chatClientService.stream(message);
    }

}
