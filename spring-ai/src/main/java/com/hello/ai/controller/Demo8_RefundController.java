package com.hello.ai.controller;

import com.hello.ai.service.RefundService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * Refund
 *
 * @author Gin
 * @since 2026-06-22
 */
@RestController
@RequestMapping("/refund")
public class Demo8_RefundController {

    @Autowired
    private RefundService refundService;

    @GetMapping("/chat")
    public Flux<String> chat(HttpServletRequest request, HttpServletResponse response, @RequestParam String message) {
        String ip = request.getRemoteAddr();
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return refundService.chat(ip, message);
    }

}
