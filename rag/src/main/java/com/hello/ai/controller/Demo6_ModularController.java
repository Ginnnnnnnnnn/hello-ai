package com.hello.ai.controller;

import com.hello.ai.service.ModularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 模块化
 *
 * @author Gin
 * @since 2026-08-06
 */
@RestController
@RequestMapping("/modular")
public class Demo6_ModularController {

    @Autowired
    private ModularService modularService;

    @RequestMapping("/chat")
    public Flux<String> chat(@RequestParam String query) {
        return modularService.chat(query);
    }

}
