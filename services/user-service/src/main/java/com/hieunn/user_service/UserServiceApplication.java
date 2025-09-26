/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service;

import com.hieunn.user_service.configs.CRLFLogConverter;
import io.github.lcaohoanq.annotations.BrowserLauncher;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// @EnableDiscoveryClient
@EnableScheduling
@BrowserLauncher(value = "http://localhost:4007/api/v1/swagger-ui.html")
public class UserServiceApplication {

  private static final Logger LOG = LoggerFactory.getLogger(UserServiceApplication.class);

  private final Environment env;

  public UserServiceApplication(Environment env) {
    this.env = env;
  }

  public static void main(String[] args) {
    var env = SpringApplication.run(UserServiceApplication.class, args);
    logApplicationStartup(env.getEnvironment());
  }

  private static void logApplicationStartup(Environment env) {
    String protocol =
        Optional.ofNullable(env.getProperty("server.ssl.key-store"))
            .map(key -> "https")
            .orElse("http");
    String applicationName = env.getProperty("spring.application.name");
    String serverPort = env.getProperty("server.port");
    String contextPath =
        Optional.ofNullable(env.getProperty("server.servlet.context-path"))
            .filter(StringUtils::isNotBlank)
            .orElse("/");
    String swaggerUIPath =
        Optional.ofNullable(env.getProperty("springdoc.swagger-ui.path"))
            .orElse("/swagger-ui.html");
    String hostAddress = "localhost";
    try {
      hostAddress = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      LOG.warn("The host name could not be determined, using `localhost` as fallback");
    }
    LOG.info(
        CRLFLogConverter.CRLF_SAFE_MARKER,
        """

            ----------------------------------------------------------
            \tApplication '{}' is running! Access URLs:
            \tLocal: \t\t{}://localhost:{}{}
            \tExternal: \t{}://{}:{}{}
            \tProfile(s): \t{}
            \tSwagger UI: \t{}://localhost:{}{}{}
            ----------------------------------------------------------""",
        applicationName,
        protocol,
        serverPort,
        contextPath,
        protocol,
        hostAddress,
        serverPort,
        contextPath,
        env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles(),
        protocol,
        serverPort,
        contextPath,
        swaggerUIPath);
  }
}
