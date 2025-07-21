package org.alfred.ticketservice.client;

import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.config.FeignConfig;
import org.alfred.ticketservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:4007/api/v1/users", configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/me/current")
    ApiResponse<UserDto> getCurrentUserFromDB(@RequestHeader("Authorization") String token);
}


