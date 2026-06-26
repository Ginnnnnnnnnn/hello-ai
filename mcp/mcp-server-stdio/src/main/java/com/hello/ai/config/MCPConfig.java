package com.hello.ai.config;

import com.hello.ai.service.TimeService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MCP
 *
 * @author Gin
 * @since 2026-06-26
 */
@Configuration
public class MCPConfig {

    @Bean
    public ToolCallbackProvider weatherTools(TimeService timeService) {
        // 自动扫描 WeatherService 中带有 @Tool 注解的方法
        return MethodToolCallbackProvider.builder()
                .toolObjects(timeService)
                .build();
    }

}
