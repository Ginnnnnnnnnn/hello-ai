package com.hello.ai.service.impl;

import com.hello.ai.service.RagService;
import com.hello.ai.strategy.DocumentReaderSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

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
            return documentReaderSelector.read(file);
        } catch (Exception e) {
            log.error("读取文件失败", e);
            throw e;
        }
    }

}
