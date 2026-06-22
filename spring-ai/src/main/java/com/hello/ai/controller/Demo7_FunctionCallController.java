package com.hello.ai.controller;

import com.hello.ai.service.FunctionCallService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * Function Call
 *
 * @author Gin
 * @since 2026-06-22
 */
@RestController
@RequestMapping("/function/call")
public class Demo7_FunctionCallController {

    @Autowired
    private FunctionCallService functionCallService;

    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return functionCallService.chat(message);
    }

}
