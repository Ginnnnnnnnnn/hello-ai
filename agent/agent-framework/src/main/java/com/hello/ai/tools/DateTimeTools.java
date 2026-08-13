package com.hello.ai.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具
 *
 * @author Gin
 * @since 2026-08-12
 */
@Slf4j
public class DateTimeTools {

    @Tool(description = "获取当前时间，返回格式：yyyy-MM-dd HH:mm:ss")
    public String getNow() {
        String result = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("获取当前时间,result:[{}]", result);
        return result;
    }

}
