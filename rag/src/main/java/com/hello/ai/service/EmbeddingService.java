package com.hello.ai.service;

import org.springframework.ai.document.Document;

import java.util.List;

/**
 * Embedding
 *
 * @author Gin
 * @since 2026-07-31
 */
public interface EmbeddingService {

    List<float[]> call(String path);

}
