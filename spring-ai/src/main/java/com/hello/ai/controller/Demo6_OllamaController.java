package com.hello.ai.controller;

import com.hello.ai.service.OllamaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * Ollama
 *
 * @author Gin
 * @since 2026-06-18
 */
@RestController
@RequestMapping("/ollama")
public class Demo6_OllamaController {

    @Autowired
    private OllamaService ollamaService;

    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return ollamaService.chat(message);
    }

}
