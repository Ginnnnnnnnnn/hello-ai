package com.hello.ai.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hello.ai.service.CleanService;
import com.hello.ai.service.EmbeddingService;
import com.hello.ai.service.ReaderService;
import com.hello.ai.service.SplitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Embedding
 *
 * @author Gin
 * @since 2026-07-31
 */
@Slf4j
@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private CleanService cleanService;

    @Autowired
    private SplitterService splitterService;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private VectorStore vectorStore;

    @Override
    public List<float[]> call(String path) {
        List<Document> documents = readerService.read(path);
        documents = cleanService.clean(documents);
        documents = splitterService.splitRecursiveCharacterText(documents);
        return documents.stream()
                .filter(item -> StrUtil.isNotBlank(item.getText()))
                .map(document -> embeddingModel.embed(document.getText()))
                .toList();
    }

    @Override
    public void store(String path) {
        List<Document> documents = readerService.read(path);
        documents = cleanService.clean(documents);
        documents = splitterService.splitRecursiveCharacterText(documents);
        int batchSize = 9;
        for (int i = 0; i < documents.size(); i += batchSize) {
            List<Document> subList = documents.subList(i, Math.min(i + batchSize, documents.size()));
            vectorStore.add(subList);
        }
    }

    @Override
    public List<Document> search(String query) {
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .filterExpression("version == 'v0.0.1'")
                        .topK(5)
                        .similarityThreshold(0.5f)
                        .build()
        );
    }

}
