package com.example.temperatureAI.service;

import com.example.temperatureAI.model.SimpleQueryResponse;
import com.example.temperatureAI.tools.TemperatureTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SpringAIService {

    private final ChatClient chatClient;

    public SpringAIService(ChatClient.Builder chatClientBuilder, TemperatureTool temperatureTool) {
        this.chatClient = chatClientBuilder
                .defaultTools(temperatureTool)
                .build();
    }

    public String simpleQueryAsString(String query) {
        return this.chatClient
                .prompt()
                .user(query)
                .call()
                .content();
    }

    public SimpleQueryResponse simpleQuery(String query) {
        return this.chatClient
                .prompt()
                .system("give answers in extreme extreme sarcastic and extreme sadist and at the same time funny manner")
                .user(query)
                .call()
                .entity(SimpleQueryResponse.class);
    }
}
