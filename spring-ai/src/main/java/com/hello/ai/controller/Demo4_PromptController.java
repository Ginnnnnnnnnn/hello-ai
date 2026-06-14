package com.hello.ai.controller;

import com.hello.ai.service.PromptService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * prompt
 *
 * @author Gin
 * @since 2026-06-14
 */
@RestController
@RequestMapping("/prompt")
public class Demo4_PromptController {

    @Autowired
    private PromptService promptService;

    @GetMapping("/role")
    public Flux<String> role(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return promptService.role(message);
    }

    @GetMapping("/shot")
    public Flux<String> shot(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return promptService.shot(message);
    }

    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return promptService.chat(message);
    }

}
