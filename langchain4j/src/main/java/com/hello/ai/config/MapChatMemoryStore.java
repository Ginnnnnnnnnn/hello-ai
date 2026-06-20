package com.hello.ai.config;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MapChatMemoryStore implements ChatMemoryStore {

    private final Map<String, String> map;

    public MapChatMemoryStore() {
        this.map = new HashMap<>();
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String key = buildKey(memoryId);
        String json = map.get(key);
        if (json == null || json.isEmpty()) {
            return Collections.emptyList();
        }
        return ChatMessageDeserializer.messagesFromJson(json);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String key = buildKey(memoryId);
        String json = ChatMessageSerializer.messagesToJson(messages);
        map.put(key, json);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        map.remove(buildKey(memoryId));
    }

    private String buildKey(Object memoryId) {
        return "langchain4j:chat-memory:" + memoryId;
    }

}