package com.hello.ai.controller;

import com.hello.ai.service.ReaderService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 读取
 *
 * @author Gin
 * @since 2026-07-31
 */
@RestController
@RequestMapping("/reader")
public class Demo1_ReaderController {

    @Autowired
    private ReaderService readerService;

    @GetMapping("/call")
    public List<Document> call(@RequestParam String path) {
        return readerService.call(path);
    }

}