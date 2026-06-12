package org.example;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpClientCaller {

    private static final String API_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";

    private static final String API_KEY;

    static {
        try (InputStream inputStream = HttpClientCaller.class.getClassLoader().getResourceAsStream("application-dev.yml")) {
            if (inputStream == null) {
                throw new IllegalStateException("application.yml not found");
            }
            Yaml yaml = new Yaml();
            Map<?, ?> config = yaml.load(inputStream);
            Object api = config.get("api");
            if (!(api instanceof Map<?, ?> apiConfig)) {
                throw new IllegalStateException("api config not found");
            }

            Object apiKey = apiConfig.get("key");
            if (apiKey == null || String.valueOf(apiKey).isBlank()) {
                throw new IllegalStateException("api.key not found");
            }

            API_KEY = String.valueOf(apiKey);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
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
            System.out.println(response.body());
        }
    }

}
