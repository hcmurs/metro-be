package com.hieunn.user_service.services;

import com.hieunn.user_service.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatBotServiceImpl implements ChatBotService {
    private final ChatModel chatModel;
    private final ChatMemory chatMemory;
    private final SystemMessage systemPrompt;

    @Override
    public String ask(String sessionId, String userMessage) {
        try {
            UserMessage message = new UserMessage(userMessage);

            // Get conversation history from memory
            var conversationHistory = chatMemory.get(sessionId);

            // Create messages list with system prompt first, then conversation history, then current message
            List<org.springframework.ai.chat.messages.Message> messages = new ArrayList<>();

            // Always include system prompt at the beginning
            messages.add(systemPrompt);

            // Add existing conversation history
            messages.addAll(conversationHistory);

            // Add current user message
            messages.add(message);

            // Create prompt with all messages
            Prompt prompt = new Prompt(messages);

            // Get response from OpenAI
            var response = chatModel.call(prompt);
            String assistantResponse = response.getResult().getOutput().toString();

            // Save both user message and assistant response to memory
            // Note: We don't save the system message to avoid duplication
            chatMemory.add(sessionId, message);
            chatMemory.add(sessionId, new AssistantMessage(assistantResponse));

            return assistantResponse;
        } catch (NonTransientAiException e){
            throw new CustomException(500, e.getMessage());
        } catch (Exception e){
            throw new CustomException(400,"Error processing chat request: " + e.getMessage());
        }
        // Create user message

    }
}
