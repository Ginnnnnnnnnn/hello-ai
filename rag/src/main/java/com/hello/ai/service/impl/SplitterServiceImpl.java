package com.hello.ai.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.transformer.splitter.RecursiveCharacterTextSplitter;
import com.hello.ai.service.CleanService;
import com.hello.ai.service.ReaderService;
import com.hello.ai.service.SplitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Splitter
 *
 * @author Gin
 * @since 2026-07-31
 */
@Slf4j
@Service
public class SplitterServiceImpl implements SplitterService {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private CleanService cleanService;

    @Override
    public List<Document> call(String path) {
        List<Document> documents = readerService.read(path);
        documents = cleanService.clean(documents);
        return splitTokenText(documents);
    }

    @Override
    public List<Document> splitTokenText(List<Document> documents) {
        TokenTextSplitter splitter = new TokenTextSplitter(
                600, 300, 5, 8000, true
        );
        return split(splitter, documents);
    }

    @Override
    public List<Document> splitRecursiveCharacterText(List<Document> documents) {
        RecursiveCharacterTextSplitter splitter = new RecursiveCharacterTextSplitter(100);
        return split(splitter, documents);
    }

    private List<Document> split(TextSplitter textSplitter, List<Document> documents) {
        if (CollUtil.isEmpty(documents)) {
            return Collections.emptyList();
        }
        return textSplitter.apply(documents);
    }

}
