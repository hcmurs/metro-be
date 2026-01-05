/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.utils;

import com.hieunn.user_service.models.ChatMessage;
import com.hieunn.user_service.repositories.ChatMessageRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostgresChatMemoryRepository implements ChatMemoryRepository {

  private final ChatMessageRepository chatMessageRepository;

  @NotNull
  @Override
  public List<String> findConversationIds() {
    return chatMessageRepository.findDistinctConversationIds();
  }

  @NotNull
  @Override
  public List<Message> findByConversationId(@NotNull String conversationId) {
    List<ChatMessage> chatMessages =
        chatMessageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId);
    return chatMessages.stream().map(this::convertToMessage).toList();
  }

  @Override
  @Transactional
  public void saveAll(@NotNull String conversationId, List<Message> messages) {
    if (!messages.isEmpty()) {
      // Convert and save messages
      List<ChatMessage> chatMessages =
          messages.stream().map(message -> convertToChatMessage(conversationId, message)).toList();

      chatMessageRepository.saveAll(chatMessages);
    }
  }

  @Override
  @Transactional
  public void deleteByConversationId(@NotNull String conversationId) {
    chatMessageRepository.deleteByConversationId(conversationId);
  }

  // Add method to save individual messages (used by ChatMemory.add())
  @Transactional
  public void saveMessage(@NotNull String conversationId, @NotNull Message message) {
    ChatMessage chatMessage = convertToChatMessage(conversationId, message);
    chatMessageRepository.save(chatMessage);
  }

  private Message convertToMessage(ChatMessage chatMessage) {
    return switch (chatMessage.getRole().toLowerCase()) {
      case "user" -> new UserMessage(chatMessage.getContent());
      case "assistant" -> new AssistantMessage(chatMessage.getContent());
      case "system" -> new SystemMessage(chatMessage.getContent());
      default ->
          throw new IllegalArgumentException("Unknown message role: " + chatMessage.getRole());
    };
  }

  private ChatMessage convertToChatMessage(String conversationId, Message message) {
    String role;

    if (message instanceof UserMessage) {
      role = "user";
    } else if (message instanceof AssistantMessage) {
      role = "assistant";
    } else if (message instanceof SystemMessage) {
      role = "system";
    } else {
      role = "unknown";
    }

    return ChatMessage.builder()
        .conversationId(conversationId)
        .role(role)
        .content(message.getText())
        .createdAt(LocalDateTime.now())
        .build();
  }
}
