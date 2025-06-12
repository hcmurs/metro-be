package com.hieunn.auth_service.controllers;

import com.hieunn.auth_service.dtos.requests.LocalLoginRequest;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.dtos.responses.UserDto;
import com.hieunn.auth_service.dtos.requests.RegisterRequest;
import com.hieunn.auth_service.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {
    AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Logout successfully"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        ApiResponse<UserDto> apiResponse = authService.register(registerRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse);
    }

    @PostMapping("/local-login")
    public ResponseEntity<ApiResponse<UserDto>> localLogin(@RequestBody LocalLoginRequest localLoginRequest) {
        ApiResponse<UserDto> apiResponse = authService.processLocalLogin(localLoginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse);
    }
}