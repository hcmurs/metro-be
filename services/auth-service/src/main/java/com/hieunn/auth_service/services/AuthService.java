package com.hieunn.auth_service.services;

import com.hieunn.auth_service.dtos.requests.LocalLoginRequest;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.dtos.responses.UserDto;
import com.hieunn.auth_service.dtos.requests.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface AuthService {
    void logout(HttpServletRequest request, HttpServletResponse response);
    ApiResponse<UserDto> processLocalLogin(LocalLoginRequest localLoginRequest);
    Map<String, Object> processMobileOAuth2Login(String code, String registrationId);
}
