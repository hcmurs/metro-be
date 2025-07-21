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