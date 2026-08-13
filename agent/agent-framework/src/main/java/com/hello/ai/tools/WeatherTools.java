package com.hello.ai.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * 天气工具
 *
 * @author Gin
 * @since 2026-08-12
 */
@Slf4j
public class WeatherTools {

    @Tool(description = "获取天气")
    public String getWeather(@ToolParam(description = "城市名称") String city) {
        String result = switch (city) {
            case "杭州市" -> "雨，25℃";
            case "北京市" -> "晴，32℃";
            default -> "未知城市";
        };
        log.info("获取天气，city:[{}],result:[{}]", city, result);
        return result;
    }

}
