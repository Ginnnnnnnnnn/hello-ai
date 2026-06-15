package com.hello.ai.service.impl;

import com.hello.ai.service.PromptService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PromptServiceImpl implements PromptService, InitializingBean {

    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;

    @Override
    public void afterPropertiesSet() {
        this.chatClient = ChatClient
                .builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @Override
    public Flux<String> role(String message) {
        return chatClient.prompt(message)
                .system("你是一个图书管理员，只会根据用户输入输出书籍，其他问题不要回答")
                .stream()
                .content();
    }

    @Override
    public Flux<String> shot(String message) {
        return chatClient.prompt(message)
                .system("""
                        你是一个图书管理员。
                        1、只会根据用户输入输出书籍
                        2、不是书籍相关的问题不要回答
                        
                        参考一下实例
                        输入：今天天气怎么样
                        输出：我是一个图书管理员，只会回答书籍相关的问题。
                        
                        输入：天气相关的书籍
                        输出：《气象学与生活》《...》
                        
                        """)
                .stream()
                .content();
    }

    @Override
    public Flux<String> chat(String message) {
        return chatClient.prompt(message)
                .system("""
                        你是一个图书管理员。
                        1、只会根据用户输入输出书籍
                        2、不是书籍相关的问题不要回答
                        3、根据指定格式输出内容
                        
                        参考对话实例
                        输入：今天天气怎么样
                        输出：我是一个图书管理员，只会回答书籍相关的问题。
                        
                        输入：天气相关的书籍
                        输出：《气象学与生活》《...》
                        
                        输出格式实例
                        书名：《XXX》
                        概要：总结内容概要
                        出版社：XXX
                        出版时间：yyyy-MM-dd
                        
                        """)
                .stream()
                .content();
    }

}
