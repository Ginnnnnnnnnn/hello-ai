package com.hello.ai.controller;

import com.hello.ai.service.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void call(@RequestParam String query) {
        retrieveService.call(query);
    }

}
