package com.hello.ai.controller;

import com.hello.ai.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * httpclient
 *
 * @author Gin
 * @since 2026-06-12
 */
@Slf4j
@RestController
@RequestMapping("/sse")
public class SseController {

    @Autowired
    private SseService sseService;

    @GetMapping("/steam")
    public SseEmitter steam() {
        return sseService.steam();
    }

}
