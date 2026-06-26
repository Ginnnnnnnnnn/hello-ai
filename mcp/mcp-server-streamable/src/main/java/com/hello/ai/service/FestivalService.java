package com.hello.ai.service;

import lombok.Data;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class FestivalService {

    @Tool(description = "获取节日")
    public String getFestival(FestivalReq req) {
        String dateTime = req.getDateTime();
        if (dateTime == null) {
            return "请填写时间";
        }
        if ("2026-06-26".contains(dateTime)) {
            return "0626节";
        } else {
            return "张三节";
        }
    }

    @Data
    public static class FestivalReq {

        @ToolParam(description = "日期，格式yyyy-MM-dd")
        private String dateTime;

    }

}