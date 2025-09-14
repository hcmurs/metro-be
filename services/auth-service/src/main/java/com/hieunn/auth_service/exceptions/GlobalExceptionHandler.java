/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.auth_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hieunn.auth_service.dtos.responses.ApiResponse;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.error(e.getStatus(), e.getMessage()));
  }

  @ExceptionHandler(FeignException.class)
  public ResponseEntity<ApiResponse<Void>> handleFeignException(FeignException e) {
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.error(e.status(), e.getMessage()));
  }
}
