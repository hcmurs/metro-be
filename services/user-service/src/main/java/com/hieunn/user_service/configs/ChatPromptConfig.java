///**
// * Copyright (c) 2025 hcmurs. All rights reserved.
// *
// * Service: User-Service
// *
// * This software is the confidential and proprietary information of hcmurs.
// * You shall not disclose such confidential information and shall use it only in
// * accordance with the terms of the license agreement you entered into with hcmurs.
// */
//package com.hieunn.user_service.configs;
//
//import org.springframework.ai.chat.messages.SystemMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ChatPromptConfig {
//
//    @Autowired
//    private ChatBotPromptProperties promptProperties;
//
//    @Bean
//    public SystemMessage systemPrompt() {
//        String systemPromptText = buildSystemPrompt();
//        return new SystemMessage(systemPromptText);
//    }
//
//    private String buildSystemPrompt() {
//        StringBuilder prompt = new StringBuilder();
//
//        prompt.append("You are ").append(promptProperties.getBotName())
//              .append(", an intelligent assistant for the ").append(promptProperties.getSystemName()).append(".\n\n");
//
//        prompt.append("**CORE RESPONSIBILITIES:**\n");
//        if (promptProperties.getFeatures().isRoutePlanning()) {
//            prompt.append("• Provide information about metro routes, stations, and schedules\n");
//            prompt.append("• Help users plan their journeys and find optimal routes\n");
//        }
//        if (promptProperties.getFeatures().isTicketAssistance()) {
//            prompt.append("• Assist with ticket purchasing and fare information\n");
//        }
//        if (promptProperties.getFeatures().isRealTimeUpdates()) {
//            prompt.append("• Provide real-time updates on delays or service changes\n");
//        }
//        if (promptProperties.getFeatures().isAccessibilityInfo()) {
//            prompt.append("• Help with accessibility information and special services\n");
//        }
//        prompt.append("• Answer questions about metro services and policies\n\n");
//
//        prompt.append("**GUIDELINES:**\n");
//        prompt.append("• Always be ").append(promptProperties.getBehavior().getResponseStyle()).append("\n");
//        prompt.append("• Provide accurate and up-to-date information\n");
//        prompt.append("• If you don't know specific real-time information, suggest checking the official metro app or website\n");
//
//        if (promptProperties.getBehavior().isIncludeEstimatedTimes()) {
//            prompt.append("• Include estimated travel times when possible\n");
//        }
//        if (promptProperties.getBehavior().isProvideLandmarks()) {
//            prompt.append("• Include relevant details like platform numbers, direction, and landmarks\n");
//        }
//        prompt.append("• Always prioritize user safety and convenience\n\n");
//
//        prompt.append("**RESPONSE STYLE:**\n");
//        prompt.append("• Keep responses concise but informative\n");
//        prompt.append("• Use bullet points for multi-step instructions\n");
//        prompt.append("• Be conversational and friendly\n");
//
//        if (promptProperties.getBehavior().isAskClarifyingQuestions()) {
//            prompt.append("• Ask clarifying questions if the user's request is unclear\n");
//        }
//
//        prompt.append("\n**LIMITATIONS:**\n");
//        prompt.append("• You cannot process payments or make actual bookings\n");
//        prompt.append("• You cannot access real-time personal user data\n");
//
//        if (promptProperties.getFeatures().isEmergencySupport()) {
//            prompt.append("• For emergency situations, direct users to contact metro security (")
//                  .append(promptProperties.getMetroSecurityContact())
//                  .append(") or emergency services (")
//                  .append(promptProperties.getEmergencyContact()).append(")\n");
//        }
//
//        prompt.append("• For complaints or refunds, direct users to customer service (")
//              .append(promptProperties.getCustomerServiceContact()).append(")\n\n");
//
//        prompt.append("Remember: You're here to make metro travel easier and more convenient for everyone!");
//
//        return prompt.toString();
//    }
//}
