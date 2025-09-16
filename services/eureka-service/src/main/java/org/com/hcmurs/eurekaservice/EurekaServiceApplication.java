/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Eureka-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.com.hcmurs.eurekaservice;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceApplication {

  public static void main(String[] args) {
    //		System.setProperty("server.port", "8761");
    var context = SpringApplication.run(EurekaServiceApplication.class, args);

    var env = context.getEnvironment();
    var activeProfiles = env.getActiveProfiles();
    List<String> profilesList = Arrays.asList(activeProfiles);

    //		if (!Arrays.asList(activeProfiles).contains("docker") &&
    // !Arrays.asList(activeProfiles).contains("test") &&
    // !Arrays.asList(activeProfiles).contains("zimaos")) {
    //			JavaBrowserLauncher.openHomePage("http://localhost:8761");
    //		}
  }
}
