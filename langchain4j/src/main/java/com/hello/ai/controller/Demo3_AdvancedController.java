package com.hello.ai.controller;

import com.hello.ai.service.AdvancedService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/advanced")
public class Demo3_AdvancedController {

    @Autowired
    private AdvancedService advancedService;

    @RequestMapping("/chat")
    public String chat(@RequestParam String message) {
        return advancedService.chat(message);
    }

    @RequestMapping("/stream")
    public Flux<String> stream(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return advancedService.stream(message);
    }

    @RequestMapping("/structure")
    public String structure(@RequestParam String message) {
        return advancedService.structure(message);
    }

    @RequestMapping("/chatMemory")
    public Flux<String> chatMemory(@RequestParam String chatId, @RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return advancedService.chatMemory(chatId, message);
    }

    @RequestMapping("/tool")
    public Flux<String> tool(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return advancedService.tool(message);
    }

}
