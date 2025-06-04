package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.dtos.requests.SocialLoginUserRequest;
import com.hieunn.user_service.dtos.responses.UserDto;
import com.hieunn.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping("/social-login")
    public ResponseEntity<ApiResponse<UserDto>> processSocialLogin(
            @Valid @RequestBody SocialLoginUserRequest socialLoginRequest
    ) {
        UserDto userDto = userService.processSocialLogin(socialLoginRequest);
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Login successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> findUser(
            @RequestHeader("Authorization") String token
    ) {
        UserDto userDto = userService.findUser(token.substring(7));
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Find successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/me2")
    public ResponseEntity<ApiResponse<UserDto>> findUser2(
            @CookieValue("token") String token
    ) {
        UserDto userDto = userService.findUser(token);
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Find successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
