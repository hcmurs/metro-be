/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Cronjob-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.cronjobservice.dtos;

import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
