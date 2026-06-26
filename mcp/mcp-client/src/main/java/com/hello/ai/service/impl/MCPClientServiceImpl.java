package com.hello.ai.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.hello.ai.enums.TypeEnum;
import com.hello.ai.service.MCPClientService;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MCP Client
 *
 * @author Gin
 * @since 2026-06-26
 */
@Slf4j
@Service
public class MCPClientServiceImpl implements MCPClientService {

    @Autowired
    private List<McpSyncClient> mcpSyncClients;

    @Autowired
    private SyncMcpToolCallbackProvider toolCallbackProvider;

    @Autowired
    private DashScopeChatModel chatModel;

    private ChatClient chatClient;

    @PostConstruct
    public void init() {
        ToolCallback[] toolCallbacks = toolCallbackProvider.getToolCallbacks();
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(100)
                .build();
        this.chatClient = ChatClient.builder(chatModel)
                .defaultToolCallbacks(toolCallbacks)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    @Override
    public Object tools(String type) {
        for (McpSyncClient client : mcpSyncClients) {
            try {
                if (client.getClientInfo().title().contains(type)) {
                    if (TypeEnum.STDIO.getType().equals(type)) {
                        McpSchema.CallToolRequest request = McpSchema.CallToolRequest.builder()
                                .name("getTime")
                                .build();
                        return client.callTool(request);
                    } else if (TypeEnum.SSE.getType().equals(type)) {
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("city", "杭州");
                        McpSchema.CallToolRequest request = McpSchema.CallToolRequest.builder()
                                .arguments(paramMap)
                                .name("getWeather")
                                .build();
                        return client.callTool(request);
                    } else if (TypeEnum.STREAMABLE.getType().equals(type)) {
                        Map<String, Object> paramReqMap = new HashMap<>();
                        paramReqMap.put("dateTime", "2026-06-26");
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("req", paramReqMap);
                        McpSchema.CallToolRequest request = McpSchema.CallToolRequest.builder()
                                .arguments(paramMap)
                                .name("getFestival")
                                .build();
                        return client.callTool(request);
                    }
                }
            } catch (Exception e) {
                log.error("MCP CALL 错误", e);
            }
        }
        return "未找到该类型的MCP";
    }

    @Override
    public Flux<String> call(String message) {
        return chatClient.prompt(new Prompt(
                        new SystemMessage("发送日期:" + DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_PATTERN)),
                        new UserMessage(message)
                ))
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, "1"))
                .stream()
                .content();
    }
}
