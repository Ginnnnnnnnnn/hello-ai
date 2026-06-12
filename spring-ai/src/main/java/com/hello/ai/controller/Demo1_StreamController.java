package com.hello.ai.controller;

import com.hello.ai.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

/**
 * 流式输出
 *
 * @author Gin
 * @since 2026-06-12
 */
@RestController
@RequestMapping("/stream")
public class Demo1_StreamController {

    @Autowired
    private StreamService streamService;

    @GetMapping("/http")
    public String http() {
        return streamService.http();
    }

    @GetMapping("/httpStream")
    public ResponseEntity<StreamingResponseBody> httpStream() {
        StreamingResponseBody responseBody = streamService.httpStream();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE)
                .body(responseBody);
    }

    @GetMapping("/sse")
    public SseEmitter sse() {
        return streamService.sse();
    }

    @GetMapping("/flux")
    public Flux<String> flux() {
        return streamService.flux();
    }

}
