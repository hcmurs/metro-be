/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.requests.ChatRequest;
import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.dtos.responses.ChatResponse;
import com.hieunn.user_service.services.ChatBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/chatbot")
@RequiredArgsConstructor
@Slf4j
public class ChatBotController {

  @Qualifier("chatBotServiceImpl")
  private final ChatBotService chatBotService;

  @PostMapping("/chat")
  public ResponseEntity<ApiResponse<ChatResponse>> chat(@RequestBody ChatRequest request) {
    try {
      log.info("Received chat request for session: {}", request.getSessionId());

      String response = chatBotService.ask(request.getSessionId(), request.getMessage());

      ChatResponse chatResponse = new ChatResponse(request.getSessionId(), response);

      log.info("Chat response generated for session: {}", request.getSessionId());

      return ResponseEntity.ok(
          ApiResponse.success(chatResponse, "Chat response generated successfully"));
    } catch (Exception e) {
      log.error("Error processing chat request: ", e);

      ChatResponse errorResponse =
          new ChatResponse(
              request.getSessionId(),
              "Sorry, max length token usage for this conversation. Please try again.");

      return ResponseEntity.status(500)
          .body(
              ApiResponse.<ChatResponse>builder()
                  .status(500)
                  .message("Internal server error occurred while processing chat request")
                  .data(errorResponse)
                  .build());
    }
  }

  @GetMapping("/health")
  public ResponseEntity<ApiResponse<String>> health() {
    return ResponseEntity.ok(
        ApiResponse.success("ChatBot service is running", "Health check successful"));
  }
}
