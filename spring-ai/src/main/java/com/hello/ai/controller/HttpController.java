package com.hello.ai.controller;

import com.hello.ai.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * http
 *
 * @author Gin
 * @since 2026-06-12
 */
@RestController
@RequestMapping("/http")
public class HttpController {

    @Autowired
    private HttpService httpService;

    @GetMapping("/steam")
    public String steam() {
        return httpService.steam();
    }

}
