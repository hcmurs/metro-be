/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.cronjobservice.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeignConfig {
  @Value("${security.api.header}")
  String header;

  @Value("${security.api.key}")
  String apiKey;

  @Bean
  public RequestInterceptor addHeaderBeforeRequest() {
    return requestTemplate -> {
      requestTemplate.header(header, apiKey);
    };
  }
}
