/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Auth-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.auth_service.feignClients;

import com.hieunn.auth_service.configs.FeignConfig;
import com.hieunn.auth_service.dtos.requests.LocalLoginRequest;
import com.hieunn.auth_service.dtos.requests.RegisterRequest;
import com.hieunn.auth_service.dtos.requests.SocialLoginUserRequest;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.dtos.responses.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "${service.users.url}", configuration = FeignConfig.class)
public interface UserServiceClient {
  @PostMapping("/social-login")
  ApiResponse<UserDto> processSocialLogin(@RequestBody SocialLoginUserRequest request);

  @PostMapping("/register")
  ApiResponse<UserDto> register(@RequestBody RegisterRequest request);

  @PostMapping("/local-login")
  ApiResponse<UserDto> processLocalLogin(@RequestBody LocalLoginRequest localLoginRequest);

  @GetMapping("/me")
  ApiResponse<UserDto> getUserProfile(@RequestHeader("Authorization") String token);
}
