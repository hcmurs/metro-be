/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "chatbot.prompt")
@Data
public class ChatBotPromptProperties {

    private String botName = "MetroBot";
    private String systemName = "Metro Transit System";
    private String customerServiceContact = "customer-service@metro.com";
    private String emergencyContact = "911";
    private String metroSecurityContact = "metro-security@metro.com";

    private Features features = new Features();
    private Behavior behavior = new Behavior();

    @Data
    public static class Features {
        private boolean routePlanning = true;
        private boolean ticketAssistance = true;
        private boolean realTimeUpdates = true;
        private boolean accessibilityInfo = true;
        private boolean fareCalculation = true;
        private boolean emergencySupport = true;
    }

    @Data
    public static class Behavior {
        private String responseStyle = "friendly and professional";
        private boolean askClarifyingQuestions = true;
        private boolean provideLandmarks = true;
        private boolean includeEstimatedTimes = true;
        private int maxConversationHistory = 20;
    }
}
