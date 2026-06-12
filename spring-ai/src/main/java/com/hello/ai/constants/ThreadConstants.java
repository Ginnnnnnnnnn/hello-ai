package com.hello.ai.constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程
 *
 * @author Gin
 * @since 2026-06-12
 */
public interface ThreadConstants {

    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

}
