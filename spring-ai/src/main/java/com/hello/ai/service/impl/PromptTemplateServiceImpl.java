package com.hello.ai.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.hello.ai.service.PromptTemplateService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@Service
public class PromptTemplateServiceImpl implements PromptTemplateService, InitializingBean {

    @Autowired
    private DashScopeChatModel chatModel;

    @Value("classpath:templates/open-source-system-prompt.st")
    private Resource resource;

    private ChatClient chatClient;

    @Override
    public void afterPropertiesSet() {
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Override
    public Flux<String> string(String message) {
        PromptTemplate promptTemplate = new PromptTemplate("给我推荐几个关于{topic}的{language}开源项目");
        promptTemplate.add("topic", message);
        promptTemplate.add("language", "Java");
        return chatClient.prompt(promptTemplate.create())
                .system("你是一个专业的的github项目收集人员")
                .stream()
                .content();
    }

    @Override
    public Flux<String> file(String message) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("topic", message);
        variables.put("language", "Java");
        PromptTemplate promptTemplate = PromptTemplate.builder()
                .resource(resource)
                .variables(variables)
                .build();
        return chatClient.prompt(promptTemplate.create())
                .system("你是一个专业的的github项目收集人员")
                .stream()
                .content();
    }

}
