package com.hieunn.auth_service.feignClients;

import com.hieunn.auth_service.configs.FeignConfig;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.dtos.requests.RegisterRequest;
import com.hieunn.auth_service.dtos.requests.SocialLoginUserRequest;
import com.hieunn.auth_service.dtos.responses.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${service.users.url}", configuration = FeignConfig.class)
public interface UserServiceClient {
    @PostMapping("/social-login")
    ApiResponse<UserDto> processSocialLogin(@RequestBody SocialLoginUserRequest request);

    @PostMapping("/register")
    ApiResponse<UserDto> register(@RequestBody RegisterRequest request);

    @GetMapping("/{usernameOrEmail}")
    ApiResponse<UserDto> findByUsernameOrEmail(@PathVariable String usernameOrEmail);
}
