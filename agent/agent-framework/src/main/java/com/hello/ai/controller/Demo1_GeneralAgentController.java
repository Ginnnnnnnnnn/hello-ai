package com.hello.ai.controller;

import com.hello.ai.service.GeneralAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * General
 *
 * @author Gin
 * @since 2026-08-12
 */
@RestController
@RequestMapping("/general")
public class Demo1_GeneralAgentController {

    @Autowired
    private GeneralAgentService generalAgentService;

    @RequestMapping("chat")
    public String chat(@RequestParam String chatId, @RequestParam String msg) {
        return generalAgentService.chat(chatId, msg);
    }

}
