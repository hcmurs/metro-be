/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.interceptors;

import com.hieunn.user_service.exceptions.ErrorMessage;
import com.hieunn.user_service.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ApiKeyInterceptor implements HandlerInterceptor {
  @Value("${security.api.key}")
  String expectedApiKey;

  @Value("${security.api.header}")
  String apiHeader;

  final ResponseUtil responseUtil;

  @Override
  public boolean preHandle(
      HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
      throws Exception {
    String apiKey = request.getHeader(apiHeader);
    if (expectedApiKey.equals(apiKey)) {
      return true;
    }

    responseUtil.sendErrorResponse(
        response, HttpServletResponse.SC_OK, ErrorMessage.INVALID_API_KEY);
    return false;
  }
}
