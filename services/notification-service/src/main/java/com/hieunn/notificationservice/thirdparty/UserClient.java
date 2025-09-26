/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.thirdparty;

import com.hieunn.notificationservice.configs.FeignConfig;
import com.hieunn.notificationservice.dtos.UserDto;
import com.hieunn.notificationservice.dtos.responses.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "user-service",
    url = "http://localhost:4007/api/v1/users",
    configuration = FeignConfig.class)
public interface UserClient {
  @GetMapping("/me/current")
  ApiResponse<UserDto> getCurrentUserFromDB(@RequestHeader("Authorization") String token);
}
