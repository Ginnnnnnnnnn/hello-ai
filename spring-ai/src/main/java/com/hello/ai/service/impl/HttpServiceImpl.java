package com.hello.ai.service.impl;

import com.hello.ai.service.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.hello.ai.constants.CommonConstants.API_URL;

/**
 * http
 *
 * @author Gin
 * @since 2026-06-12
 */
@Slf4j
@Service
public class HttpServiceImpl implements HttpService {

    @Value("${api.key}")
    private String API_KEY;

    @Override
    public String steam() {
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

}
