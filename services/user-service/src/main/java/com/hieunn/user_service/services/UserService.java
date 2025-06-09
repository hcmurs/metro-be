package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.SocialLoginUserRequest;
import com.hieunn.user_service.dtos.responses.UserDto;

public interface UserService {
    UserDto processSocialLogin(SocialLoginUserRequest socialLoginUserRequest);
    UserDto findUser(String token);
}
