package com.hello.ai.controller;

import com.hello.ai.service.EmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Embedding
 *
 * @author Gin
 * @since 2026-07-31
 */
@RestController
@RequestMapping("/embedding")
public class EmbeddingController {

    @Autowired
    private EmbeddingService embeddingService;

    @RequestMapping("/call")
    public void call(@RequestParam String path) {
        embeddingService.call(path);
    }

}
