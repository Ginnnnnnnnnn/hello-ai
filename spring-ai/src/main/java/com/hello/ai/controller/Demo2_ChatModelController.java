package com.hello.ai.controller;

import com.hello.ai.service.ChatModelService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * ChatModel
 *
 * @author Gin
 * @since 2026-06-12
 */
@RestController
@RequestMapping("/chatModel")
public class Demo2_ChatModelController {

    @Autowired
    private ChatModelService chatModelService;

    @GetMapping("/call/string")
    public String callString(@RequestParam String message) {
        return chatModelService.callString(message);
    }

    @GetMapping("/call/message")
    public String callMessage(@RequestParam String message) {
        return chatModelService.callMessage(message);
    }

    @GetMapping("/call/prompt")
    public String callPrompt(@RequestParam String message) {
        return chatModelService.callPrompt(message);
    }

    @GetMapping("/stream/string")
    public Flux<String> streamString(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return chatModelService.streamString(message);
    }

    @GetMapping("/stream/message")
    public Flux<String> streamMessage(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return chatModelService.streamMessage(message);
    }

    @GetMapping("/stream/prompt")
    public Flux<String> streamPrompt(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        Flux<ChatResponse> chatResponseFlux = chatModelService.streamPrompt(message);
        return chatResponseFlux.mapNotNull(chatResponse -> chatResponse.getResult().getOutput().getText());
    }

}
