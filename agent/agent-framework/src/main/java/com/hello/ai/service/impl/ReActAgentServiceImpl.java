package com.hello.ai.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import com.alibaba.cloud.ai.graph.streaming.StreamingOutput;
import com.hello.ai.service.ReActAgentService;
import com.hello.ai.tools.DateTimeTools;
import com.hello.ai.tools.WeatherTools;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * ReAct
 *
 * @author Gin
 * @since 2026-08-12
 */
@Service
public class ReActAgentServiceImpl implements ReActAgentService {

    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private ToolCallingManager toolCallingManager;

    @Override
    public String chat(String chatId, String msg) {
        ChatOptions chatOptions = ToolCallingChatOptions.builder()
                .toolCallbacks(ToolCallbacks.from(new DateTimeTools(), new WeatherTools()))
                .internalToolExecutionEnabled(false)
                .build();
        // 第一次对话加入系统提示词
        if (CollUtil.isEmpty(chatMemory.get(chatId))) {
            chatMemory.add(chatId, new SystemMessage("""
                    你是一个基于React架构（Reasoning-Act-Observation）的智能助手，你擅长使用工具帮我解决问题。
                    你的工作流程是：
                        "1、思考：先根据用户的提问进行思考，推理出下一步需要进行的具体系统
                        "2、行动：做具体的行动，这一步可以使用工具
                        "3、观察：记录前一步行动的结果。你可以进行多轮思考和行动。如果要使用工具，请务必调用工具，不要自己随便捏造结果。
                    """));
        }
        // 加入用户提示词
        chatMemory.add(chatId, new UserMessage(msg));
        // 定义 Prompt
        Prompt prompt = new Prompt(chatMemory.get(chatId), chatOptions);
        // 调用大模型
        ChatResponse chatResponse = chatModel.call(prompt);
        chatMemory.add(chatId, chatResponse.getResult().getOutput());
        // 循环处理工具调用
        while (chatResponse.hasToolCalls()) {
            //执行工具调用
            ToolExecutionResult toolExecutionResult = toolCallingManager.executeToolCalls(prompt, chatResponse);
            chatMemory.add(chatId, toolExecutionResult.conversationHistory().getLast());
            //调用模型
            prompt = new Prompt(chatMemory.get(chatId), chatOptions);
            chatResponse = chatModel.call(prompt);
            chatMemory.add(chatId, chatResponse.getResult().getOutput());
        }
        for (Message message : chatMemory.get(chatId)) {
            System.out.println(message);
        }
        return chatResponse.getResult().getOutput().getText();
    }

    @Override
    public String chatAlibaba(String chatId, String msg) {
        ReactAgent agent = ReactAgent.builder()
                .name("my-react-agent")
                .model(chatModel)
                .tools(ToolCallbacks.from(new DateTimeTools(), new WeatherTools()))
                .systemPrompt("""
                        你是一个基于React架构（Reasoning-Act-Observation）的智能助手，你擅长使用工具帮我解决问题。
                        你的工作流程是：
                            "1、思考：先根据用户的提问进行思考，推理出下一步需要进行的具体系统
                            "2、行动：做具体的行动，这一步可以使用工具
                            "3、观察：记录前一步行动的结果。你可以进行多轮思考和行动。如果要使用工具，请务必调用工具，不要自己随便捏造结果。
                        """)
                .saver(new MemorySaver())
                .build();
        RunnableConfig config = RunnableConfig.builder()
                .threadId(chatId)
                .build();
        try {
            return agent.call(msg, config).getText();
        } catch (GraphRunnerException e) {
            return e.getMessage();
        }
    }

    @Override
    public Flux<String> chatAlibabaStream(String chatId, String msg) {
        ReactAgent agent = ReactAgent.builder()
                .name("my-react-agent")
                .model(chatModel)
                .tools(ToolCallbacks.from(new DateTimeTools(), new WeatherTools()))
                .systemPrompt("""
                        你是一个基于React架构（Reasoning-Act-Observation）的智能助手，你擅长使用工具帮我解决问题。
                        你的工作流程是：
                            "1、思考：先根据用户的提问进行思考，推理出下一步需要进行的具体系统
                            "2、行动：做具体的行动，这一步可以使用工具
                            "3、观察：记录前一步行动的结果。你可以进行多轮思考和行动。如果要使用工具，请务必调用工具，不要自己随便捏造结果。
                        """)
                .saver(new MemorySaver())
                .build();
        RunnableConfig config = RunnableConfig.builder()
                .threadId(chatId)
                .build();
        try {
            return agent.stream(msg, config)
                    .map(output -> {
                        if (output instanceof StreamingOutput) {
                            Message message = ((StreamingOutput<?>) output).message();
                            return message != null ? message.getText() : "";
                        } else {
                            String nodeId = output.node();
                            return "节点 " + nodeId + " 执行完成 \r";
                        }
                    });
        } catch (GraphRunnerException e) {
            return Flux.interval(Duration.ofSeconds(1))
                    .map(i -> e.getMessage());
        }

    }

}
