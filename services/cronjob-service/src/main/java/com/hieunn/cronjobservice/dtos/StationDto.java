package com.hieunn.cronjobservice.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationDto {
    Long stationId;
    String stationCode;
    String name;
    String address;
    Double latitude;
    Double longitude;
    Integer sequenceOrder;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Long routeId;
}
