package com.hello.ai.strategy;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class JsonReaderStrategy implements DocumentReaderStrategy {

    public boolean supports(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".json");
    }

    @Override
    public List<Document> read(File file) {
        Resource resource = new FileSystemResource(file);
        // 提取 json 的 description、content 字段
        return new JsonReader(resource, "description", "content").get();
    }

}