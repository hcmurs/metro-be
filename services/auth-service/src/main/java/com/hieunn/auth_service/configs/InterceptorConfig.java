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

import com.hieunn.auth_service.interceptors.ApiKeyInterceptor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
  ApiKeyInterceptor apiKeyInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(apiKeyInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html");
  }
}
