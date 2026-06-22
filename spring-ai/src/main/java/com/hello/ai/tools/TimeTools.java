package com.hello.ai.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TimeTools {

    @Tool(description = "获取当前时间")
    public String getTimeByZoneId(
            @ToolParam(description = "时区标识。例如：Asia/Shanghai。默认：Asia/Shanghai。") String zoneId
    ) {
        ZoneId zid = ZoneId.of(zoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String format = zonedDateTime.format(formatter);
        log.info("getTimeByZoneId:[{}]", format);
        return format;
    }

}
