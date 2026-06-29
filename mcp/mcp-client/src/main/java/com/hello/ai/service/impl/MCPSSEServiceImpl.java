package com.hello.ai.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.hello.ai.service.MCPSSEService;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * MCP SSE
 *
 * @author Gin
 * @since 2026-06-29
 */
@Slf4j
@Service
public class MCPSSEServiceImpl implements MCPSSEService {

    @Autowired
    private DashScopeChatModel chatModel;

    @Value("${spring.ai.mcp.client.sse.connections.weather-sse.url}")
    private String sseUrl;

    @Value("${spring.ai.mcp.client.sse.connections.weather-sse.sse-endpoint}")
    private String sseEndpoint;

    private ChatClient chatClient;

    private McpSyncClient sseClient;

    // 是否正在重试 initialize（保证唯一性）
    private final AtomicBoolean retrying = new AtomicBoolean(false);

    // initialize 重试线程
    private final ExecutorService retryExecutor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        this.sseClient = buildClient();
        this.chatClient = buildChatClient();
    }

    /**
     * 定时任务：每 5 秒 ping 一次 SSE
     * ping 不通则触发 initialize 重试线程
     */
    @Scheduled(fixedDelay = 5000)
    public void pingSse() {
        log.info("SSE MCP ping...");
        if (sseClient == null) {
            log.warn("SSE client not initialized yet.");
            startRetryInitialize();
            return;
        }
        try {
            sseClient.ping();
            log.debug("SSE MCP ping OK.");
        } catch (Exception e) {
            log.error("SSE MCP ping failed: {}", e.getMessage());
            startRetryInitialize();
        }
    }

    @Override
    public Flux<String> chat(String message) {
        return chatClient.prompt(new Prompt(
                        new SystemMessage("发送日期:" + DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_PATTERN)),
                        new UserMessage(message)
                ))
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, "1"))
                .stream()
                .content();
    }

    private McpSyncClient buildClient() {
        HttpClientSseClientTransport transport = HttpClientSseClientTransport
                .builder(sseUrl)
                .sseEndpoint(sseEndpoint)
                .build();
        McpSyncClient mcpSyncClient = McpClient.sync(transport)
                .clientInfo(new McpSchema.Implementation("sse-client", "1.0"))
                .requestTimeout(Duration.ofSeconds(10))
                .build();
        mcpSyncClient.initialize();
        return mcpSyncClient;
    }

    private ChatClient buildChatClient() {
        SyncMcpToolCallbackProvider provider = SyncMcpToolCallbackProvider.builder()
                .mcpClients(List.of(this.sseClient))
                .build();
        ToolCallback[] callbacks = provider.getToolCallbacks();
        return ChatClient.builder(chatModel)
                .defaultToolCallbacks(callbacks)
                .defaultTools()
                .build();
    }

    private void startRetryInitialize() {
        // 保证只启动一个重试线程
        if (!retrying.compareAndSet(false, true)) {
            return;
        }
        retryExecutor.submit(() -> {
            log.warn("Start retrying SSE MCP initialize...");
            while (true) {
                try {
                    // 重建 sseClient
                    this.sseClient = buildClient();
                    log.info("SSE MCP re-initialized successfully.");
                    this.chatClient = buildChatClient();
                    retrying.set(false);
                    return;
                } catch (Exception e) {
                    log.error("Retry initialize failed, will retry in 10s. Reason: {}", e.getMessage());
                }
                ThreadUtil.sleep(10000);
            }
        });
    }

}
