package com.hello.ai.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.hello.ai.service.StructureOutputService;
import com.hello.ai.structure.AnimeStructure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * StructureOutput
 *
 * @author Gin
 * @since 2026-06-15
 */
@Slf4j
@Service
public class StructureOutputServiceImpl implements StructureOutputService, InitializingBean {

    @Autowired
    private DashScopeChatModel chatModel;

    private ChatClient chatClient;

    @Override
    public void afterPropertiesSet() {
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultSystem("""
                        1、你是一个优秀的动漫推荐专家
                        2、不是动漫相关的问题不要回答
                        3、优先输出作品的中文名称
                        """)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Override
    public Flux<String> object(String message) {
        BeanOutputConverter<Object> beanOutputConverter = new BeanOutputConverter<>(Object.class);
        String format = beanOutputConverter.getFormat();
        return chatClient.prompt(new Prompt(
                        new SystemMessage(format),
                        new UserMessage(message)
                ))
                .stream()
                .content();
    }

    @Override
    public String anime(String message) {
        BeanOutputConverter<AnimeStructure> beanOutputConverter = new BeanOutputConverter<>(AnimeStructure.class);
        String format = beanOutputConverter.getFormat();
        String content = chatClient.prompt(new Prompt(
                        new SystemMessage(format),
                        new UserMessage(message)
                ))
                .call()
                .content();
        AnimeStructure animeStructure = null;
        if (content != null) {
            animeStructure = beanOutputConverter.convert(content);
            log.info("AnimeStructure:[{}]", animeStructure);
        }
        return content;
    }

    @Override
    public String animeList(String message) {
        List<AnimeStructure> animeStructures = chatClient.prompt(new Prompt(
                        new UserMessage(message)
                ))
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
        log.info("animeStructures:[{}]", animeStructures);
        if (animeStructures == null) {
            return "[]";
        }
        return animeStructures.toString();
    }

}
