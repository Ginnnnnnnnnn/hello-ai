package com.hello.ai.controller;

import com.hello.ai.service.CleanService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 清洗
 *
 * @author Gin
 * @since 2026-07-31
 */
@RestController
@RequestMapping("/clean")
public class Demo2_CleanController {

    @Autowired
    private CleanService cleanService;

    @GetMapping("/call")
    public List<Document> call(@RequestParam String path) {
        return cleanService.call(path);
    }

}
