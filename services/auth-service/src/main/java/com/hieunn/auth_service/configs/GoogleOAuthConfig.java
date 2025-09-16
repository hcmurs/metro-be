/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Auth-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.auth_service.configs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
@Component
@Data
@NoArgsConstructor
public class GoogleOAuthConfig {
  private String clientId;
}
