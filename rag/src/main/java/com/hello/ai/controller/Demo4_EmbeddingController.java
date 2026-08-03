package com.hello.ai.controller;

import com.hello.ai.service.EmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 向量化
 *
 * @author Gin
 * @since 2026-07-31
 */
@RestController
@RequestMapping("/embedding")
public class Demo4_EmbeddingController {

    @Autowired
    private EmbeddingService embeddingService;

    @RequestMapping("/call")
    public List<float[]> call(@RequestParam String path) {
        return embeddingService.call(path);
    }

    @RequestMapping("/store")
    public String store(@RequestParam String path) {
        embeddingService.store(path);
        return "success";
    }

}
