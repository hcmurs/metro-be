/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob;

import io.github.lcaohoanq.JavaBrowserLauncher;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderApplication {

  public static void main(String[] args) {
    var context = SpringApplication.run(OrderApplication.class, args);

    var env = context.getEnvironment();
    var activeProfiles = env.getActiveProfiles();
    if (!Arrays.asList(activeProfiles).contains("docker")
        && !Arrays.asList(activeProfiles).contains("test")
        && !Arrays.asList(activeProfiles).contains("zimaos")) {
      JavaBrowserLauncher.openHomePage("http://localhost:4009/swagger-ui.html");
    }
  }
}
