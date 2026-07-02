package com.hello.ai.controller;

import com.hello.ai.service.RagService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/read")
public class RagController {

    @Autowired
    private RagService ragService;

    @GetMapping("/test")
    public List<Document> test(@RequestParam String path) {
        return ragService.test(path);
    }

}