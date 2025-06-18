package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.LocalLoginRequest;
import com.hieunn.user_service.dtos.requests.RegisterRequest;
import com.hieunn.user_service.dtos.requests.SocialLoginRequest;
import com.hieunn.user_service.dtos.responses.UserDto;

import java.util.List;

public interface UserService {
    UserDto processSocialLogin(SocialLoginRequest socialLoginRequest);
    UserDto processLocalLogin(LocalLoginRequest localLoginRequest);
    UserDto findUser(String token);
    List<UserDto> findAll(String token);
    UserDto register(RegisterRequest registerRequest);
    boolean isUsernameExist(String username);
    boolean isEmailExist(String email);
    void resetPassword(String email, String newPassword);
}