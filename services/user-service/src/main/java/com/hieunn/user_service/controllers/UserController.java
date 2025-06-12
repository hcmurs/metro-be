package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.requests.RegisterRequest;
import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.dtos.requests.SocialLoginRequest;
import com.hieunn.user_service.dtos.responses.UserDto;
import com.hieunn.user_service.models.User;
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
            @Valid @RequestBody SocialLoginRequest socialLoginRequest) {
        UserDto userDto = userService.processSocialLogin(socialLoginRequest);
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Login successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> findUser(
            @RequestHeader("Authorization") String token) {
        UserDto userDto = userService.findUser(token.substring(7));
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Find successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(
            @Valid @RequestBody RegisterRequest registerRequest) {
        UserDto userDto = userService.register(registerRequest);
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Register successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{usernameOrEmail}")
    public ResponseEntity<ApiResponse<User>> findByUsernameOrEmail(
            @PathVariable String usernameOrEmail) {
        User user = userService.findByUsernameOrEmail(usernameOrEmail);
        ApiResponse<User> response = ApiResponse.success(user, "Login successfully");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
