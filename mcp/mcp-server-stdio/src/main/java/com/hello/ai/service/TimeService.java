package com.hello.ai.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeService {

    @Tool(description = "获取当前时间")
    public String getTime() {
        return DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATETIME_PATTERN);
    }

}