/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.auth_service.services;

import com.hieunn.auth_service.dtos.requests.LocalLoginRequest;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.dtos.responses.TokenResponse;
import com.hieunn.auth_service.dtos.responses.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
  void logout(HttpServletRequest request, HttpServletResponse response);

  ApiResponse<UserDto> processLocalLogin(LocalLoginRequest localLoginRequest);

  ApiResponse<TokenResponse> processGoogleLogin(String idToken);

  ApiResponse<UserDto> getUserProfileFromToken(String authorizationHeader);
}
