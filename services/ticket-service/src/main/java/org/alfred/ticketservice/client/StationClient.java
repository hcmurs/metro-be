package org.alfred.ticketservice.client;

import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.dto.station.StationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "station-service", url = "http://localhost:4004", path = "/api/stations")
public interface StationClient {
    @GetMapping("/{id}")
    ApiResponse<StationResponse> getStationById(@PathVariable("id") Long id);

    @GetMapping("/check-line")
    ApiResponse<Boolean> checkStationOnLine(@RequestParam("startStationId") Long startStationId,
                                             @RequestParam("endStationId") Long endStationId,
                                             @RequestParam("thisStation") Long thisStation);

    @GetMapping("/{id}/exists")
    ApiResponse<Boolean> checkStationExists(@PathVariable("id") Long id);
}
