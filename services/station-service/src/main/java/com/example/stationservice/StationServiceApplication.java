/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Station-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.stationservice;

import io.github.lcaohoanq.annotations.BrowserLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@BrowserLauncher(
    value = "http://localhost:4007/swagger-ui/index.html",
    healthCheckEndpoint = "http://localhost:4007/api/v1/actuator/health")
public class StationServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(StationServiceApplication.class, args);
  }
}
