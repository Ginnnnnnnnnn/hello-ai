package com.hello.ai.controller;

import com.hello.ai.service.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 召回
 *
 * @author Gin
 * @since 2026-08-04
 */
@RestController
@RequestMapping("/retrieve")
public class Demo5_RetrieveController {

    @Autowired
    private RetrieveService retrieveService;

    @RequestMapping("/call")
    public Flux<String> call(@RequestParam String query) {
        return retrieveService.call(query);
    }

}
