package com.hello.ai.controller;

import com.hello.ai.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 多模态
 *
 * @author Gin
 * @since 2026-08-10
 */
@RestController
@RequestMapping("/multimodal")
public class Demo7_MediaController {

    @Autowired
    private MediaService mediaService;

    @RequestMapping("/call")
    public Flux<String> call(@RequestParam String url, @RequestParam String msg) {
        return mediaService.call(url, msg);
    }

}
