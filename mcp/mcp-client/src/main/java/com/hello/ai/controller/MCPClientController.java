package com.hello.ai.controller;

import com.hello.ai.service.MCPClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * MCP Client
 *
 * @author Gin
 * @since 2026-06-26
 */
@RestController
@RequestMapping("/chat")
public class MCPClientController {

    @Autowired
    private MCPClientService mcpClientService;

    @RequestMapping("/tools")
    public Object tools(@RequestParam String type) {
        return mcpClientService.tools(type);
    }

    @RequestMapping("/call")
    public Flux<String> call(@RequestParam String message) {
        return mcpClientService.call(message);
    }

}
