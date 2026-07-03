package com.hello.ai.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hello.ai.service.RagService;
import com.hello.ai.strategy.DocumentReaderSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RagServiceImpl implements RagService {

    @Autowired
    private DocumentReaderSelector documentReaderSelector;

    @Override
    public List<Document> test(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("文件不存在或不是有效文件: " + path);
        }
        try {
            List<Document> documents = documentReaderSelector.read(file);
            return cleanDocuments(documents);
        } catch (Exception e) {
            log.error("读取文件失败", e);
            throw e;
        }
    }

    /**
     * 文本清洗
     */
    public List<Document> cleanDocuments(List<Document> documents) {
        if (CollUtil.isEmpty(documents)) {
            return documents;
        }
        return documents.stream()
                .map(doc -> {
                    if (doc == null || doc.getText() == null) {
                        return doc;
                    }

                    String text = doc.getText();

                    // 1. 去掉多余空白字符（空格、制表符、换行等）
                    text = text.replaceAll("\\s+", " ").trim();

                    // 2. 去掉无意义的乱码或特殊符号
                    text = text.replaceAll("[^\\p{L}\\p{N}\\p{P}\\p{Z}\\n]", "");

                    // 3. 可选：统一大小写
                    // text = text.toLowerCase();

                    // 4. 按换行拆分段落，去除重复段落
                    String[] paragraphs = text.split("\\n+");
                    Set<String> seen = new LinkedHashSet<>();
                    for (String para : paragraphs) {
                        String trimmed = para.trim();
                        if (!trimmed.isEmpty()) {
                            seen.add(trimmed);
                        }
                    }

                    text = String.join("\n", seen);

                    return new Document(text);
                })
                .collect(Collectors.toList());
    }

}
