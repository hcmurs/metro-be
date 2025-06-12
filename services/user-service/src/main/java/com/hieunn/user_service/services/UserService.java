package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.RegisterRequest;
import com.hieunn.user_service.dtos.requests.SocialLoginRequest;
import com.hieunn.user_service.dtos.responses.UserDto;
import com.hieunn.user_service.models.User;

public interface UserService {
    UserDto processSocialLogin(SocialLoginRequest socialLoginRequest);
    User findByUsernameOrEmail(String usernameOrEmail);
    UserDto findUser(String token);
    UserDto register(RegisterRequest registerRequest);
}