package com.hello.ai.service.impl;

import com.hello.ai.service.SimpleService;
import com.hello.ai.tools.TemperatureTools;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.*;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ToolChoice;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchema;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static dev.langchain4j.model.chat.request.ResponseFormatType.JSON;

/**
 * 低层次
 *
 * @author Gin
 * @since 2026-06-18
 */
@Service
public class SimpleServiceImpl implements SimpleService {

    @Autowired
    private OpenAiChatModel chatModel;

    @Autowired
    private OpenAiStreamingChatModel streamingChatModel;

    private ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(100);

    @Override
    public String chat(String message) {
        return chatModel.chat(message);
    }

    @Override
    public Flux<String> stream(String message) {
        return Flux.create(fluxSink -> streamingChatModel.chat(message, new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String partialResponse) {
                fluxSink.next(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                fluxSink.complete();
            }

            @Override
            public void onError(Throwable error) {
                fluxSink.error(error);
            }
        }));
    }

    @Override
    public String memory(String message) {
        UserMessage userMessage = new UserMessage(message);
        chatMemory.add(userMessage);
        AiMessage aiMessage = chatModel.chat(chatMemory.messages()).aiMessage();
        chatMemory.add(aiMessage);
        return aiMessage.text();
    }

    @Override
    public String structure(String message) {
        ResponseFormat responseFormat = ResponseFormat.builder()
                .type(JSON)
                .jsonSchema(JsonSchema.builder()
                        .name("book")
                        .rootElement(JsonObjectSchema.builder()
                                .addStringProperty("name")
                                .addStringProperty("time")
                                .addStringProperty("description")
                                .required("name", "time", "description")
                                .build())
                        .build())
                .build();
        ChatRequest chatRequest = ChatRequest.builder()
                .responseFormat(responseFormat)
                .messages(
                        SystemMessage.from("""
                                John is 42 years old and lives an independent life.
                                He stands 1.75 meters tall and carries himself with confidence.
                                Currently unmarried, he enjoys the freedom to focus on his personal goals and interests.
                                """),
                        UserMessage.from(message)
                )
                .build();
        return chatModel.chat(chatRequest).aiMessage().text();
    }

    @Override
    public String tool(String message) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        // 1、定义工具列表
        List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(TemperatureTools.class);
        // 2.构造用户提示词
        UserMessage userMessage = UserMessage.from(message);
        chatMessages.add(userMessage);
        // 3. 创建ChatRequest，并指定工具列表
        ChatRequest request = ChatRequest.builder()
                .messages(userMessage)
                .toolSpecifications(toolSpecifications)
                .toolChoice(ToolChoice.AUTO)
                .build();
        // 4. 调用模型
        ChatResponse response = chatModel.chat(request);
        AiMessage aiMessage = response.aiMessage();
        // 5.把模型结果添加到chatMessages中
        chatMessages.add(aiMessage);
        // 6.执行工具
        List<ToolExecutionRequest> toolExecutionRequests = response.aiMessage().toolExecutionRequests();
        toolExecutionRequests.forEach(toolExecutionRequest -> {
            ToolExecutor toolExecutor = new DefaultToolExecutor(new TemperatureTools(), toolExecutionRequest);
            System.out.println("execute tool " + toolExecutionRequest.name());
            String result = toolExecutor.execute(toolExecutionRequest, UUID.randomUUID().toString());
            ToolExecutionResultMessage toolExecutionResultMessages = ToolExecutionResultMessage.from(toolExecutionRequest, result);
            // 7.把工具执行结果添加到chatMessages中
            chatMessages.add(toolExecutionResultMessages);
        });
        // 8.重新构造ChatRequest，并使用之前的对话chatMessages，以及指定toolSpecifications
        ChatRequest finalRequest = ChatRequest.builder()
                .messages(chatMessages)
                .toolSpecifications(toolSpecifications)
                .build();
        // 9.调用模型
        ChatResponse finalChatResponse = chatModel.chat(finalRequest);
        return finalChatResponse.aiMessage().text();
    }

}
