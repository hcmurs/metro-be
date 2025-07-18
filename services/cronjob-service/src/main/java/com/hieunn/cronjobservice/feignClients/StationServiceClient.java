package com.hieunn.cronjobservice.feignClients;

import com.hieunn.cronjobservice.configs.FeignConfig;
import com.hieunn.cronjobservice.dtos.ApiResponse;
import com.hieunn.cronjobservice.dtos.StationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "station-service", url = "${service.stations.url}", configuration = FeignConfig.class)
public interface StationServiceClient {
    @GetMapping()
    ApiResponse<List<StationDto>> findAllStations();
}
