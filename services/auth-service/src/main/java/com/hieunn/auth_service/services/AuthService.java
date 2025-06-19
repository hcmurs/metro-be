package com.hieunn.auth_service.services;

import com.hieunn.auth_service.dtos.requests.LocalLoginRequest;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.dtos.responses.TokenResponse;
import com.hieunn.auth_service.dtos.responses.UserDto;
import com.hieunn.auth_service.dtos.requests.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void logout(HttpServletRequest request, HttpServletResponse response);
    ApiResponse<UserDto> processLocalLogin(LocalLoginRequest localLoginRequest);
    ApiResponse<TokenResponse> processGoogleLogin(String idToken);

}
