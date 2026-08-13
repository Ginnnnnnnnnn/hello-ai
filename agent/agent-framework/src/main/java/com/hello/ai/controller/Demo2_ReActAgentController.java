package com.hello.ai.controller;

import com.hello.ai.service.ReActAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * ReAct
 *
 * @author Gin
 * @since 2026-08-12
 */
@RestController
@RequestMapping("/react")
public class Demo2_ReActAgentController {

    @Autowired
    private ReActAgentService reActAgentService;

    @RequestMapping("chat")
    public String chat(@RequestParam String chatId, @RequestParam String msg) {
        return reActAgentService.chat(chatId, msg);
    }

    @RequestMapping("/chatAlibaba")
    public String chatAlibaba(@RequestParam String chatId, @RequestParam String msg) {
        return reActAgentService.chatAlibaba(chatId, msg);
    }

    @RequestMapping("/chatAlibabaStream")
    public Flux<String> chatAlibabaStream(@RequestParam String chatId, @RequestParam String msg) {
        return reActAgentService.chatAlibabaStream(chatId, msg);
    }

}
