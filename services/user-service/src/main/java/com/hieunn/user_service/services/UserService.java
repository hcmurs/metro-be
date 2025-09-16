/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.LocalLoginRequest;
import com.hieunn.user_service.dtos.requests.RegisterRequest;
import com.hieunn.user_service.dtos.requests.SocialLoginRequest;
import com.hieunn.user_service.dtos.responses.UserDto;
import com.hieunn.user_service.models.User;
import java.util.List;

public interface UserService {
  UserDto processSocialLogin(SocialLoginRequest socialLoginRequest);

  UserDto processLocalLogin(LocalLoginRequest localLoginRequest);

  User getCurrentUser();

  UserDto getCurrentUserDto();

  List<UserDto> findAll();

  UserDto register(RegisterRequest registerRequest);

  boolean isUsernameExist(String username);

  boolean isEmailExist(String email);

  void resetPassword(String email, String newPassword);

  UserDto findByUserId(Long userId);

  UserDto findByEmail();
}
