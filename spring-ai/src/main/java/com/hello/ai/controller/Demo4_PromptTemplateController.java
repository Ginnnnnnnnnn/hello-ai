package com.hello.ai.controller;

import com.hello.ai.service.PromptTemplateService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * PromptTemplate
 *
 * @author Gin
 * @since 2026-06-14
 */
@RestController
@RequestMapping("/prompt/template")
public class Demo4_PromptTemplateController {

    @Autowired
    private PromptTemplateService promptTemplateService;

    @GetMapping("/string")
    public Flux<String> template(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return promptTemplateService.string(message);
    }

    @GetMapping("/file")
    public Flux<String> file(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return promptTemplateService.file(message);
    }

}
