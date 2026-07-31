package com.hello.ai.service;

import org.springframework.ai.document.Document;

import java.util.List;

/**
 * Clean
 *
 * @author Gin
 * @since 2026-07-31
 */
public interface CleanService {

    List<Document> call(String path);

    List<Document> clean(List<Document> documents);

}
