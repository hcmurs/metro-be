///**
// * Copyright (c) 2025 hcmurs. All rights reserved.
// *
// * Service: User-Service
// *
// * This software is the confidential and proprietary information of hcmurs.
// * You shall not disclose such confidential information and shall use it only in
// * accordance with the terms of the license agreement you entered into with hcmurs.
// */
//package com.hieunn.user_service.services;
//
//import com.hieunn.user_service.configs.ChatBotPromptProperties;
//import lombok.RequiredArgsConstructor;
//import org.springframework.ai.chat.messages.SystemMessage;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EnhancedChatBotService implements ChatBotService {
//
//    @Qualifier("chatBotServiceImpl")
//    private final ChatBotService baseChatBotService;
//    private final PromptTemplateService promptTemplateService;
//    private final ChatBotPromptProperties promptProperties;
//
//    @Override
//    public String ask(String sessionId, String userMessage) {
//        // Analyze the message to determine the appropriate prompt template
//        PromptType promptType = analyzeMessageForPromptType(userMessage);
//
//        // For now, delegate to the base service, but this could be enhanced
//        // to use different prompts based on the detected type
//        return baseChatBotService.ask(sessionId, userMessage);
//    }
//
//    public String askWithPromptType(String sessionId, String userMessage, PromptType promptType) {
//        // This method allows explicit prompt type specification
//        // Implementation would switch the system prompt based on type
//        // For now, delegating to base service
//        return baseChatBotService.ask(sessionId, userMessage);
//    }
//
//    private PromptType analyzeMessageForPromptType(String userMessage) {
//        String lowerMessage = userMessage.toLowerCase();
//
//        // Simple keyword-based analysis
//        if (lowerMessage.contains("emergency") || lowerMessage.contains("help") ||
//            lowerMessage.contains("stuck") || lowerMessage.contains("accident")) {
//            return PromptType.EMERGENCY;
//        }
//
//        if (lowerMessage.contains("first time") || lowerMessage.contains("new to") ||
//            lowerMessage.contains("beginner") || lowerMessage.contains("never used")) {
//            return PromptType.NEW_USER;
//        }
//
//        return PromptType.DEFAULT;
//    }
//
//    public enum PromptType {
//        DEFAULT,
//        NEW_USER,
//        EMERGENCY,
//        CUSTOM
//    }
//}
