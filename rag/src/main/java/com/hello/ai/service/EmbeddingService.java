package com.hello.ai.service;

import java.util.List;

/**
 * Embedding
 *
 * @author Gin
 * @since 2026-07-31
 */
public interface EmbeddingService {

    List<float[]> call(String path);

    void store(String path);

}
