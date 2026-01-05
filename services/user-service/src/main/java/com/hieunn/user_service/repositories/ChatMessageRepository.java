/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.repositories;

import com.hieunn.user_service.models.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
  List<ChatMessage> findByConversationIdOrderByCreatedAtAsc(String conversationId);

  @Query("SELECT DISTINCT c.conversationId FROM ChatMessage c")
  List<String> findDistinctConversationIds();

  void deleteByConversationId(String conversationId);
}
