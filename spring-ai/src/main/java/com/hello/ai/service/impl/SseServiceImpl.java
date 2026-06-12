package com.hello.ai.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.hello.ai.constants.ThreadConstants;
import com.hello.ai.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * httpclient
 *
 * @author Gin
 * @since 2026-06-12
 */
@Slf4j
@Service
public class SseServiceImpl implements SseService {

    @Value("${api.key}")
    private String API_KEY;

    @Override
    public SseEmitter steam() {
        SseEmitter sseEmitter = new SseEmitter();
        ThreadConstants.executor.submit(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    ThreadUtil.sleep(1000L);
                    sseEmitter.send("message" + i);
                }
            } catch (IOException e) {
                sseEmitter.completeWithError(e);
            } finally {
                sseEmitter.complete();
            }
        });
        return sseEmitter;
    }

}
