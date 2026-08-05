package com.hello.ai.service.impl;

import com.hello.ai.reader.DocumentReaderSelector;
import com.hello.ai.service.ReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Reader
 *
 * @author Gin
 * @since 2026-07-31
 */
@Slf4j
@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private DocumentReaderSelector documentReaderSelector;

    @Override
    public List<Document> call(String path) {
        return read(path);
    }

    @Override
    public List<Document> read(String path) {
        ClassPathResource resource = new ClassPathResource("file/" + path);
        if (!resource.exists()) {
            throw new IllegalArgumentException("文件不存在: " + path);
        }
        try {
            File file = resource.getFile();
            if (!file.isFile()) {
                throw new IllegalArgumentException("文件不存在或不是有效文件: " + path);
            }
            List<Document> documents = documentReaderSelector.read(file);
            for (Document document : documents) {
                document.getMetadata().put("version", "v0.0.1");
            }
            return documents;
        } catch (Exception e) {
            log.error("读取文件失败", e);
            return null;
        }
    }

}
