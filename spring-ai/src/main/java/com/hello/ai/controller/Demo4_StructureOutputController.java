package com.hello.ai.controller;

import com.hello.ai.service.StructureOutputService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * StructureOutput
 *
 * @author Gin
 * @since 2026-06-14
 */
@RestController
@RequestMapping("/structure/output")
public class Demo4_StructureOutputController {

    @Autowired
    private StructureOutputService structureOutputService;

    @GetMapping("/object")
    public Flux<String> object(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return structureOutputService.object(message);
    }

    @GetMapping("/anime")
    public String anime(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return structureOutputService.anime(message);
    }

    @GetMapping("/animeList")
    public String animeList(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        return structureOutputService.animeList(message);
    }

}
