/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Station-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.stationservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Route")
public class BusRoutes {
  @Id private String id;
  private String name;
  private double distance;
  private int duration;
  private String start_time;
  private String end_time;
  private int is_active;
  private String route_num;
  private String direction;
  private String trip_spacing;
}
