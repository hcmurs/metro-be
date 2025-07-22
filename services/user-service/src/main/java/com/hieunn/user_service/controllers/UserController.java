package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.requests.LocalLoginRequest;
import com.hieunn.user_service.dtos.requests.RegisterRequest;
import com.hieunn.user_service.dtos.requests.ResetPasswordRequest;
import com.hieunn.user_service.dtos.requests.SocialLoginRequest;
import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.dtos.responses.UserDto;
import com.hieunn.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser() {
        UserDto userDto = userService.getCurrentUserDto();
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Find successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDto>> findByUserId(@PathVariable Long userId) {
        UserDto userDto = userService.findByUserId(userId);
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Find successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(
            @Valid @RequestBody RegisterRequest registerRequest) {
        UserDto userDto = userService.register(registerRequest);
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Register successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/local-login")
    public ResponseEntity<ApiResponse<UserDto>> findByUsernameOrEmail(
            @RequestBody LocalLoginRequest localLoginRequest) {
        UserDto userDto = userService.processLocalLogin(localLoginRequest);
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Login successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-username-exist")
    public ResponseEntity<ApiResponse<?>> isUsernameExist(
            @RequestParam String username) {
        boolean isUsernameExist = userService.isUsernameExist(username);
        ApiResponse<?> response = isUsernameExist ?
                ApiResponse.success(true, "Username already exists") :
                ApiResponse.success(false, "Username does not exist");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-email-exist")
    public ResponseEntity<ApiResponse<?>> isEmailExist(
            @RequestParam String email) {
        boolean isEmailExist = userService.isEmailExist(email);
        ApiResponse<?> response = isEmailExist ?
                ApiResponse.success(true, "Email already exists") :
                ApiResponse.success(false, "Email does not exist");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        userService.resetPassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success("Reset password successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> findAll() {
        List<UserDto> users = userService.findAll();
        ApiResponse<List<UserDto>> response = ApiResponse.success(users, "Find successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/current")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUserFromDB() {
        UserDto userDto = userService.findByEmail();
        ApiResponse<UserDto> response = ApiResponse.success(userDto, "Find successfully");
        return ResponseEntity.ok(response);
    }


}
