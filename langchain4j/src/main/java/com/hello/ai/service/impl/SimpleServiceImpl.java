package com.hello.ai.service.impl;

import com.hello.ai.service.SimpleService;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * 低层次
 *
 * @author Gin
 * @since 2026-06-18
 */
@Service
public class SimpleServiceImpl implements SimpleService {

    @Autowired
    private OpenAiChatModel chatModel;

    @Autowired
    private OpenAiStreamingChatModel streamingChatModel;

    @Override
    public String chat(String message) {
        return chatModel.chat(message);
    }

    @Override
    public Flux<String> stream(String message) {
        return Flux.create(fluxSink -> streamingChatModel.chat(message, new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String partialResponse) {
                fluxSink.next(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                fluxSink.complete();
            }

            @Override
            public void onError(Throwable error) {
                fluxSink.error(error);
            }
        }));
    }

}
