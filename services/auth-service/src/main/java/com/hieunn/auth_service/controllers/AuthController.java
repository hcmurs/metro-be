/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.auth_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hieunn.auth_service.dtos.requests.GoogleLoginRequest;
import com.hieunn.auth_service.dtos.requests.LocalLoginRequest;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.dtos.responses.TokenResponse;
import com.hieunn.auth_service.dtos.responses.UserDto;
import com.hieunn.auth_service.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {
  AuthService authService;

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Void>> logout(
      HttpServletRequest request, HttpServletResponse response) {
    authService.logout(request, response);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Logout successfully"));
  }

  @PostMapping("/local-login")
  public ResponseEntity<ApiResponse<UserDto>> localLogin(
      @RequestBody LocalLoginRequest localLoginRequest) {
    ApiResponse<UserDto> apiResponse = authService.processLocalLogin(localLoginRequest);
    return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
  }

  @PostMapping("/oauth2/google")
  public ResponseEntity<ApiResponse<TokenResponse>> authenticateWithGoogle(
      @RequestBody GoogleLoginRequest request) {
    ApiResponse<TokenResponse> response = authService.processGoogleLogin(request.getIdToken());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/profile")
  public ResponseEntity<ApiResponse<UserDto>> getUserProfile(
      @RequestHeader("Authorization") String authorizationHeader) {
    ApiResponse<UserDto> response = authService.getUserProfileFromToken(authorizationHeader);
    return ResponseEntity.status(response.getStatus()).body(response);
  }
}
