/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Gateway-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.com.hcmurs.gatewayservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class BeanConfig {
  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    redisTemplate.setDefaultSerializer(new StringRedisSerializer());
    return redisTemplate;
  }

  @Bean
  public PathPatternParser patternParser() {
    return new PathPatternParser();
  }
}
