package com.hello.ai.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * Function Call Config
 *
 * @author Gin
 * @since 2026-06-23
 */
@Slf4j
@Configuration
public class FunctionConfig {

    @Bean
    @Description("获取当前时间")
    public Function<Request, Response> getTimeFunction() {
        return FunctionConfig::getTimeByZoneId;
    }

    public static Response getTimeByZoneId(Request request) {
        ZoneId zid = ZoneId.of(request.zoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String format = zonedDateTime.format(formatter);
        log.info("getTimeByZoneId Function :[{}]", format);
        return new Response(format);
    }

    public record Request(
            @JsonProperty(required = true, value = "zoneId")
            @JsonPropertyDescription("时区标识。例如：Asia/Shanghai。默认：Asia/Shanghai。")
            String zoneId
    ) {

    }

    public record Response(String time) {

    }

}
