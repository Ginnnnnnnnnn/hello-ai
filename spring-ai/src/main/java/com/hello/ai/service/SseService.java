package com.hello.ai.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * httpclient
 *
 * @author Gin
 * @since 2026-06-12
 */
public interface SseService {

    SseEmitter steam();

}
