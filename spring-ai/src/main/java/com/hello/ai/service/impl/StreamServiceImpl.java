package com.hello.ai.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.hello.ai.constants.ThreadConstants;
import com.hello.ai.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static com.hello.ai.constants.CommonConstants.API_URL;

/**
 * 流式输出
 *
 * @author Gin
 * @since 2026-06-12
 */
@Slf4j
@Service
public class StreamServiceImpl implements StreamService {

    @Value("${spring.ai.dashscope.api-key}")
    private String API_KEY;

    @Override
    public String http() {
        String requestBody = """
                {
                    "model": "qwen-plus",
                    "messages": [
                        {
                            "role": "system",
                            "content": "You are a helpful assistant."
                        },
                        {
                            "role": "user",
                            "content": "你是谁"
                        }
                    ],
                    "stream": true
                }
                """;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("X-DashScope-SSE", "enable")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            log.error("发生异常", e);
            throw new RuntimeException();
        }
    }

    @Override
    public StreamingResponseBody httpStream() {
        return outputStream -> {
            for (int i = 0; i < 10; i++) {
                ThreadUtil.sleep(1000L);
                outputStream.write(("message" + i).getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        };
    }

    @Override
    public SseEmitter sse() {
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

    @Override
    public Flux<String> flux() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> "message" + i);
    }
}
