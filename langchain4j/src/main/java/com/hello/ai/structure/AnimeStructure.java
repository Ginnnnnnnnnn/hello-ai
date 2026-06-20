package com.hello.ai.structure;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.time.LocalDateTime;

/**
 * 动漫
 *
 * @author Gin
 * @since 2026-06-15
 */
public record AnimeStructure(

        @JsonPropertyDescription("动漫名称")
        String name,

        @JsonPropertyDescription("上映时间")
        LocalDateTime time,

        @JsonPropertyDescription("作品简介")
        String description

) {
}
