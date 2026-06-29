package com.hello.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MCPClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MCPClientApplication.class, args);
    }

}