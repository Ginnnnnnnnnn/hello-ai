package com.hello.ai.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.transformer.splitter.RecursiveCharacterTextSplitter;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文档工具类
 *
 * @author Gin
 * @since 2026-07-28
 */
public class DocumentUtils {

    public static List<Document> cleanDocuments(List<Document> documents) {
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

    public static List<Document> splitTokenText(List<Document> documents) {
        /*
          chunkSize：每块最多 600 tokens
          minChunkSizeChars：每块至少 400 字符再考虑断点
          minChunkLengthToEmbed：太短的不做嵌入
          maxNumChunks：最多拆分8000块
          keepSeparator：保留句号、换行符
         */
        TokenTextSplitter splitter = new TokenTextSplitter(
                600, 300, 5, 8000, true
        );
        return split(splitter, documents);
    }

    public static List<Document> splitRecursiveCharacterText(List<Document> documents) {
        RecursiveCharacterTextSplitter splitter = new RecursiveCharacterTextSplitter(100);
        return split(splitter, documents);
    }

    public static List<Document> split(TextSplitter textSplitter, List<Document> documents) {
        if (CollUtil.isEmpty(documents)) {
            return Collections.emptyList();
        }
        return textSplitter.apply(documents);
    }

}
