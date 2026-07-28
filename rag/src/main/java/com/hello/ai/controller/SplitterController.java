package com.hello.ai.controller;

import com.hello.ai.service.SplitterService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/splitter")
public class SplitterController {

    @Autowired
    private SplitterService splitterService;

    @RequestMapping("/call")
    public List<Document> call(@RequestParam String path) {
        return splitterService.call(path);
    }

}
