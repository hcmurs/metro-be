/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.cronjobservice.feignClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.hieunn.cronjobservice.configs.FeignConfig;
import com.hieunn.cronjobservice.dtos.ApiResponse;
import com.hieunn.cronjobservice.dtos.StationDto;

@FeignClient(
    name = "station-service",
    url = "${service.stations.url}",
    configuration = FeignConfig.class)
public interface StationServiceClient {
  @GetMapping()
  ApiResponse<List<StationDto>> findAllStations();
}
