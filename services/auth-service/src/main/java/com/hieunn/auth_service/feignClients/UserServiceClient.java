package com.hieunn.auth_service.feignClients;

import com.hieunn.auth_service.configs.FeignConfig;
import com.hieunn.auth_service.dtos.ApiResponse;
import com.hieunn.auth_service.dtos.SocialLoginUserRequest;
import com.hieunn.auth_service.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${service.users.url}", configuration = FeignConfig.class)
public interface UserServiceClient {
    @PostMapping("/social-login")
    ApiResponse<UserDto> processSocialLogin(@RequestBody SocialLoginUserRequest request);

}
