package org.alfred.ticketservice.client;

import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.dto.station.StationResponse;
import org.alfred.ticketservice.dto.station.StationRouteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "station-service", url = "http://localhost:4004", path = "/api/station-routes")
public interface StationClient {
    @GetMapping("/route/{routeId}")
    ApiResponse<List<StationRouteResponse>> getStationRoutesByRouteId(@PathVariable("routeId") Long routeId);

    @GetMapping("/check-station-on-line")
    ApiResponse<Boolean> checkStationOnLine(@RequestParam("startStationId") Long startStationId,
                                             @RequestParam("endStationId") Long endStationId,
                                             @RequestParam("thisStationId") Long thisStationId);

    @GetMapping("{id}")
    ApiResponse<StationRouteResponse> getStationRouteById(@RequestParam("id") Long id);


}
