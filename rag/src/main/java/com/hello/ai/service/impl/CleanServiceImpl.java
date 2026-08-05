package com.hello.ai.service.impl;

import com.hello.ai.service.CleanService;
import com.hello.ai.service.ReaderService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Clean
 *
 * @author Gin
 * @since 2026-07-31
 */
@Service
public class CleanServiceImpl implements CleanService {

    @Autowired
    private ReaderService readerService;

    @Override
    public List<Document> call(String path) {
        List<Document> documents = readerService.read(path);
        return clean(documents);
    }

    @Override
    public List<Document> clean(List<Document> documents) {
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

                    return new Document(text, doc.getMetadata());
                })
                .collect(Collectors.toList());
    }

}
