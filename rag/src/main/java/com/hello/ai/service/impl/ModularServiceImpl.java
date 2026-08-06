package com.hello.ai.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.hello.ai.service.ModularService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * modular
 *
 * @author Gin
 * @since 2026-08-06
 */
@Service
public class ModularServiceImpl implements ModularService, InitializingBean {

    @Autowired
    private PgVectorStore vectorStore;

    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;

    @Override
    public void afterPropertiesSet() {
        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultOptions(
                        DashScopeChatOptions.builder()
                                .temperature(0.5)
                                .build()
                )
                .build();
    }

    @Override
    public Flux<String> chat(String query) {
        DocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.5)
                .topK(5)
                .build();

        RewriteQueryTransformer rewriteQueryTransformer = RewriteQueryTransformer.builder()
                .chatClientBuilder(ChatClient.builder(chatModel).build().mutate())
                .build();

        MultiQueryExpander multiQueryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(ChatClient.builder(chatModel).build().mutate())
                .numberOfQueries(3)
                .includeOriginal(true)
                .build();


        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                // 检索阶段：从向量库检索文档（必需）
                .documentRetriever(documentRetriever)
                // 查询预处理：转换查询（可选）
                .queryTransformers(rewriteQueryTransformer)
                // 查询预处理：扩展查询（可选）
                .queryExpander(multiQueryExpander)
                // 后处理阶段：合并文档（当使用查询扩展时推荐）
//                .documentJoiner(multiQueryExpander)
                // 生成阶段：构建增强提示词（可选，有默认实现）
//                .queryAugmenter(queryAugmenter)
                .build();

        return chatClient.prompt(query)
                .advisors(retrievalAugmentationAdvisor)
                .stream()
                .content();
    }

}
