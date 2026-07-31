package com.hello.ai.service;

import org.springframework.ai.document.Document;

import java.util.List;

/**
 * Splitter
 *
 * @author Gin
 * @since 2026-07-31
 */
public interface SplitterService {

    List<Document> call(String path);

    /**
     * 固定大小分块
     * chunkSize：每块最多 600 tokens
     * minChunkSizeChars：每块至少 400 字符再考虑断点
     * minChunkLengthToEmbed：太短的不做嵌入
     * maxNumChunks：最多拆分8000块
     * keepSeparator：保留句号、换行符
     *
     * @param documents 文档
     * @return List<Document>
     * @author Gin
     * @since 2026-07-31
     */
    List<Document> splitTokenText(List<Document> documents);

    /**
     * 递归分块
     * chunkSize：每块最多 600 tokens
     *
     * @param documents 文档
     * @return List<Document>
     * @author Gin
     * @since 2026-07-31
     */
    List<Document> splitRecursiveCharacterText(List<Document> documents);

}
