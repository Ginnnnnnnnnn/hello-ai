package com.hello.ai.controller;

import com.hello.ai.service.SimpleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * 低层次
 *
 * @author Gin
 * @since 2026-06-18
 */
@RestController
@RequestMapping("/simple")
public class Demo2_SimpleController {

    @Autowired
    private SimpleService simpleService;

    @RequestMapping("/chat")
    public String chat(@RequestParam String message) {
        return simpleService.chat(message);
    }

    @RequestMapping("/stream")
    public Flux<String> stream(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return simpleService.stream(message);
    }

    @RequestMapping("/memory")
    public String memory(@RequestParam String message) {
        return simpleService.memory(message);
    }

    @RequestMapping("/structure")
    public String structure(@RequestParam String message) {
        return simpleService.structure(message);
    }

    @RequestMapping("/tool")
    public String tool(@RequestParam String message) {
        return simpleService.tool(message);
    }

}
