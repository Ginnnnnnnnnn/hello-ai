package com.hello.ai.controller;

import com.hello.ai.service.MCPSSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * MCP SSE
 *
 * @author Gin
 * @since 2026-06-29
 */
@RestController
@RequestMapping("/sse")
public class MCPSSEController {

    @Autowired
    private MCPSSEService mcpsseService;

    @RequestMapping("/chat")
    public Flux<String> chat(@RequestParam String message) {
        return mcpsseService.chat(message);
    }

}
