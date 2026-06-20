package com.hello.ai.service.ai;

import com.hello.ai.structure.AnimeStructure;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface LangChainAiService {

    @SystemMessage("结尾必须有一个emoji")
    String chat(String userMessage);

    @SystemMessage("结尾必须有一个emoji")
    @UserMessage("针对用户的内容：{{userMessage}}，先复述一遍他的问题，然后再回答")
    Flux<String> chatStream(String userMessage);

    @SystemMessage({"你是一个专业的动漫推荐家", "优先用中文"})
    AnimeStructure anime(String userMessage);

}