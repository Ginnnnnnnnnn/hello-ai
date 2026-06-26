package com.hello.ai.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MCP 类型
 *
 * @author Gin
 * @since 2026-06-26
 */
@Getter
@AllArgsConstructor
public enum TypeEnum {

    STDIO("stdio"),
    SSE("sse"),
    STREAMABLE("streamable"),
    ;

    private final String type;

}
