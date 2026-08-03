package com.hello.ai.controller;

import com.hello.ai.service.SplitterService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分片
 *
 * @author Gin
 * @since 2026-07-31
 */
@RestController
@RequestMapping("/splitter")
public class Demo3_SplitterController {

    @Autowired
    private SplitterService splitterService;

    @RequestMapping("/call")
    public List<Document> call(@RequestParam String path) {
        return splitterService.call(path);
    }

}
